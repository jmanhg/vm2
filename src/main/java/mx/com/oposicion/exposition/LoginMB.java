package mx.com.oposicion.exposition;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import mx.com.oposicion.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;


@Controller
@Scope("view")
public class LoginMB implements Serializable {

    private HttpSession session= (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    
    private static final long serialVersionUID = 1L;
    
    
    
    public String login() {
        System.out.println("loging.............");
        
        return genericLog("/j_spring_security_check");
        
    }

    public String logout() {
        System.out.println("logout.............");

        return genericLog("/j_spring_security_logout");
    }

    public String genericLog(String target) {
        try {
            return manageLoginLogout(target);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String manageLoginLogout(String url) throws ServletException, IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        ServletRequest request = (ServletRequest) context.getRequest();
        ServletResponse response = (ServletResponse) context.getResponse();

        RequestDispatcher dispatcher = request.getRequestDispatcher(url);

        dispatcher.forward(request, response);
        FacesContext.getCurrentInstance().responseComplete();
        return null;
    }
    
    public void printUsuarioActual() {
        if (session.getAttribute("Usuario") != null) {
            Usuario user= (Usuario) session.getAttribute("Usuario");
            System.out.println(user.getNombre()+" "+user.getApaterno());
            List<GrantedAuthority> perfiles=user.getPerfiles();
            System.out.println("Perfiles:");
            for(GrantedAuthority ga: perfiles){
                System.out.println("Perfiles->"+ga.getAuthority());
            }
            
        } else {
            System.out.println("Anónimo");
        }
    }

    public String getUsuarioActual() {
        if (session.getAttribute("Usuario") != null) {
            Usuario user= (Usuario) session.getAttribute("Usuario");
            printUsuarioActual();
            return ""+user.getNombre()+" "+user.getApaterno();
        } else {
            return "Anónimo";
        }
    }
    
    public boolean getAccessRol(String rol) {
        if (session.getAttribute("Usuario") != null) {
           Usuario user= (Usuario) session.getAttribute("Usuario");
            //System.out.println(user.getNombre()+" "+user.getApaterno());
            List<GrantedAuthority> perfiles=user.getPerfiles();
            //System.out.println("Perfiles:");
            for(GrantedAuthority ga: perfiles){
                //System.out.println("Perfiles->"+ga.getAuthority());
                if(rol.toUpperCase().contains(ga.getAuthority().toUpperCase())){
                    return true;
                }
            }
        } 
        return false;
        
    }
    
    public Usuario getUsuario() {
        if (session.getAttribute("Usuario") != null) {
            Usuario user= (Usuario) session.getAttribute("Usuario");
            return user;
        } else {
            return null;
        }
    }
}
