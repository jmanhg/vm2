package mx.com.oposicion.service;

import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import mx.com.oposicion.exposition.PojoLista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mx.com.oposicion.model.Usuario;
import mx.com.oposicion.persistence.UsuarioMapper;
import mx.com.oposicion.support.Audit;
import mx.com.oposicion.support.Enconding;
import mx.com.oposicion.support.Util;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    @Autowired
    private UsuarioMapper usuarioMapper;
    
     @Autowired
    private CatVienaService catVienaService;
     
    private List<PojoLista> listaClientes = new ArrayList<PojoLista>();
   
    
    
    @Autowired
    private SessionRegistry sessionRegistry;
    
    private Usuario user = null;
    private static final long WAIT_MILIS = 300000; // 5 mniutos

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException,AuthenticationException {

        if(expireUserSessions(username)){
            this.user=null;
           throw new AuthenticationException("Ya existe una session con el usuario:"+username) {};
        }

        UserDetails userDetails = usuarioMapper.getUsuarioByName(username);
        if (userDetails == null) {
            return new Usuario();
        }
        this.user = (Usuario) userDetails;
        //System.out.println("USUARIO:"+user.getUsername()+":PASSWORD:"+user.getPassword()+":ENCODE:"+Enconding.MD5(user.getPassword())+"");
        
        if(FacesContext.getCurrentInstance()!=null)
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("Usuario", this.user);
        
        Audit.write(001, "Login:"+this.user.getUsername());
                        


        if (!user.isCuentaNoBloqueada() && (user.getInstanteDeBloqueo().getTime() + WAIT_MILIS - System.currentTimeMillis()) <= 0) {
            user.setCuentaNoBloqueada(true);
            user.setInstanteDeBloqueo(null);
            user.setContadorIntentosFallidos(0);
            updateIntentosFallidos(user);
            desBloquearUsuario(user);

        }
        
        if(FacesContext.getCurrentInstance()!=null){
//        Util.updateFaceContextCatViena(catVienaService.getAll());
        }
        
        
        
        
        
        return user;
    }
public boolean expireUserSessions(String username) {
    boolean ret=false;
    System.out.println("Usuarios conectados");
    
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            if (principal instanceof Usuario) {
                Usuario userDetails = (Usuario) principal;
                System.out.println("Usuario:"+userDetails.getUsername());
                if (userDetails.getUsername().equals(username)) {
                   
                    
                    for (SessionInformation information : sessionRegistry.getAllSessions(userDetails, true)) {
                        System.out.println("El usuario:"+userDetails.getUsername()+" id:"+information.getSessionId()+" Expied:"+information.isExpired());
                        if(!information.isExpired()){
                        System.out.println("El usuario:"+userDetails.getUsername()+" ya esta en session");
                        //return true;
                        }
                    }
                }
            }
        }
    return ret;
    }
    public Usuario getUsuario() {
        return this.user;
    }

    @Override
    public void updateIntentosFallidos(Usuario user) {
        System.out.println("updating......");
        usuarioMapper.updateIntentosFallidos(user);
    }

    @Override
    public void bloquearUsuario(Usuario user) {
        usuarioMapper.bloquearUsuario(user);
    }

    @Override
    public void desBloquearUsuario(Usuario user) {
        usuarioMapper.desBloquearUsuario(user);
    }

    @Override
    public List<Usuario> getAll() {
        return usuarioMapper.getAll();
    }

    @Override
    public void inserta(Usuario p) {

        usuarioMapper.insert(p);
    }

    @Override
    public void actualiza(Usuario p) {

        usuarioMapper.update(p);
    }

    @Override
    public void borra(Usuario p) {

        usuarioMapper.delete(p);
    }

    @Override
    public Usuario encuentra(int id) {
        return usuarioMapper.findById(id);
    }
    
    @Override
    public Usuario findByIdClave(Usuario p) {
        return usuarioMapper.findByIdClave(p);
    }
    
}
