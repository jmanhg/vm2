package mx.com.oposicion.security;

import java.io.IOException;
import java.sql.Timestamp;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import mx.com.oposicion.support.Enconding;
import mx.com.oposicion.model.Usuario;
import mx.com.oposicion.service.UsuarioService;
import mx.com.oposicion.support.Audit;
import org.springframework.security.authentication.encoding.PasswordEncoder;

@Service
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
        //@Value("${propiedad.MAX_INTENTOS}")
	private static final int MAX_INTENTOS = 30;
	private static final long WAIT_MILIS = 300000; // 5 mniutos

	@Value("${defaultFailureUrl}")
	private String defaultFailureUrl;
	
	@Autowired
	private UsuarioService usuarioService;
	
        @Autowired
        private PasswordEncoder customPasswordEncoder;
	
        @Override
	public void onAuthenticationFailure(HttpServletRequest request,
	        HttpServletResponse response, AuthenticationException exception)
	        throws IOException, ServletException {
		System.out.println("failed");
                
        
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ERROR", getError(exception) );
                super.setDefaultFailureUrl(defaultFailureUrl);
                super.onAuthenticationFailure(request, response, exception);
	}
	
	private String getError(Exception e){
		String error = null;
		System.out.println("error:  " + e.getMessage() );
		
		Usuario user = usuarioService.getUsuario();
		
                if( user != null ){
                    //System.out.println("USUARIO:"+user.getUsername()+":PASSWORD:"+user.getPassword()+":ENCODE:"+Enconding.MD5(user.getPassword())+"");
                
			if( e instanceof BadCredentialsException ){
				increaseCounterFailure(user);
				error = "Usuario o contraseña erronea, intento : " + user.getContadorIntentosFallidos() + " de " + MAX_INTENTOS;
                                
			}
			else if( e instanceof LockedException ){
				error = "Usuario bloqueado, intente en " + ( ( ( WAIT_MILIS + user.getInstanteDeBloqueo().getTime() ) - System.currentTimeMillis() ) / 60000 ) + " minutos";
			}
			else if( e instanceof AccountExpiredException){
				error = "Cuenta desactivada";
			}else if(e instanceof AuthenticationException){
                        error = ""+e.getMessage();
                        }
                        Audit.write(002, "Fallo Login:"+user.getUsername()+":"+error);
		}
		else{
                    if(e instanceof AuthenticationException){
                        error = ""+e.getMessage();
                        Audit.write(002, "Fallo Login:"+error);
                    }else{
			error = "El usuario no existe o ocurrio un error al conectarse.";
                        Audit.write(002, "Fallo Login:Anonimo:"+error);
                    }
                }
		
		return error;
	}
	
	private void increaseCounterFailure(Usuario user){
		System.out.println("Incrementando cuenta...." + user.getContadorIntentosFallidos());
		user.setContadorIntentosFallidos(user.getContadorIntentosFallidos() +1);
		System.out.println("Incrementado a...." + user.getContadorIntentosFallidos());
		usuarioService.updateIntentosFallidos(user);
		if( user.getContadorIntentosFallidos() == MAX_INTENTOS && user.isCuentaNoBloqueada()){
			System.out.println("bloqueando....");
			user.setCuentaNoBloqueada(false);
			user.setInstanteDeBloqueo( new Timestamp(System.currentTimeMillis()) );
			usuarioService.bloquearUsuario(user);
		}
	}
}
