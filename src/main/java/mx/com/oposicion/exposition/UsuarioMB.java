/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.oposicion.exposition;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import mx.com.oposicion.model.Usuario;
import mx.com.oposicion.service.UsuarioService;
import mx.com.oposicion.support.MessagesController;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import mx.com.oposicion.exposition.LoginMB;
import mx.com.oposicion.support.Enconding;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;


@Controller
@Scope(value = "view")
//@Scope("request")
//@RequestScoped
public class UsuarioMB implements Serializable {
    
    private final static long serialVersionUID = 1L;
    
    private DualListModel<String> cities; 
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private MessagesController msgControler;
    
    @Value("${menssages.debug}")
    private boolean msgDebug;
    
    private List<Usuario> usuarios;
    private UsuarioDataModel usuarioDataModel;
    
    @Autowired
    private SessionRegistry sessionRegistry;
    
    
    private Usuario usuarioSelected = new Usuario();
    private Usuario passwordSelected = new Usuario();
    LoginMB loginMB=new LoginMB();
    
    private String passwordActual;
    private String passwordNuevo;
    private String passwordNuevo2;
    

    public Usuario getUsuarioSelected() {
        return usuarioSelected;
    }

    public void setUsuarioSelected(Usuario usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }

    public Usuario getPasswordSelected() {
        return passwordSelected;
    }

    public void setPasswordSelected(Usuario passwordSelected) {
        this.passwordSelected = passwordSelected;
    }

    public String getPasswordActual() {
        return passwordActual;
    }

    public void setPasswordActual(String passwordActual) {
        this.passwordActual = passwordActual;
    }

    public String getPasswordNuevo() {
        return passwordNuevo;
    }

    public void setPasswordNuevo(String passwordNuevo) {
        this.passwordNuevo = passwordNuevo;
    }

    public String getPasswordNuevo2() {
        return passwordNuevo2;
    }

    public void setPasswordNuevo2(String passwordNuevo2) {
        this.passwordNuevo2 = passwordNuevo2;
    }

    
    
    @PostConstruct
    public void init() {
    	usuarios = usuarioService.getAll();
        usuarioDataModel=new UsuarioDataModel(usuarios);
        
        agregarSession(0);
        
        //Cities  
        List<String> citiesSource = new ArrayList<String>();  
        List<String> citiesTarget = new ArrayList<String>();  
          
        citiesSource.add("Istanbul");  
        citiesSource.add("Ankara");  
        citiesSource.add("Izmir");  
        citiesSource.add("Antalya");  
        citiesSource.add("Bursa");  
          
        cities = new DualListModel<String>(citiesSource, citiesTarget);  
        
       passwordSelected= loginMB.getUsuario();
        
    }

    public DualListModel<String> getCities() {
        return cities;
    }

    public void setCities(DualListModel<String> cities) {
        this.cities = cities;
    }

    public UsuarioDataModel getUsuarioDataModel() {
        return usuarioDataModel;
    }

    public void setUsuarioDataModel(UsuarioDataModel usuarioDataModel) {
        this.usuarioDataModel = usuarioDataModel;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    
    
    
    public List<Usuario> getUsuarios() {
    	return usuarios;
    }
    
    public void modifica(ActionEvent ae) {
        
    	if(usuarioSelected.getIdUsuario()<1) {
            if(msgDebug) System.out.println("insert.getId_usuario():"+usuarioSelected.getIdUsuario()+usuarioSelected.getNombre());
    		usuarioService.inserta(usuarioSelected);
    	} else {
            if(msgDebug) System.out.println("update.getId_usuario():"+usuarioSelected.getIdUsuario()+usuarioSelected.getNombre());
    		usuarioService.actualiza(usuarioSelected);
    	}
    	usuarios = usuarioService.getAll();
        usuarioDataModel=new UsuarioDataModel(usuarios);
    }
    
    public void modificaPassword(ActionEvent ae) {
        
    	if(passwordSelected.getIdUsuario()>1) {
           
            if(passwordActual==null || passwordActual.equals("")){
                msgControler.addInfo("Información:", "Password actual requerido");
                return ;
            }
            if(passwordNuevo==null || passwordNuevo.equals("")){
                msgControler.addInfo("Información:", "Password Nuevo requerido");
                return;
            }
            if(passwordNuevo2==null || passwordNuevo2.equals("")){
                msgControler.addInfo("Información:", "Password Nuevo 2 requerido");
                return;
            }
            
            
            if(passwordNuevo!=null && passwordNuevo2!=null){
                if(!passwordNuevo.equals(passwordNuevo2)){
                    msgControler.addInfo("Información:", "Password Nuevo es diferente a Password Nuevo 2");
                    return;
                }
            }
            passwordSelected.setClave(Enconding.MD5(passwordActual));
           if(usuarioService.findByIdClave(passwordSelected)==null){
                msgControler.addInfo("Información:", "Password actual incorrecto");
            }else{
                Usuario u=usuarioService.findByIdClave(passwordSelected);
                if(u!=null){
                    u.setPassword(Enconding.MD5(passwordNuevo));
                    usuarioService.actualiza(u);
                    msgControler.addInfo("Información:", "Password Actualizado");
                }
           }
    	}
    }
    public void borraSession(Usuario usuarioSelected) {
        if(usuarioSelected !=null && usuarioSelected.getSessionId().length()>=0) {
    		if(msgDebug) System.out.println("usuarioSelected.getSessionId():"+usuarioSelected.getSessionId());
                eliminarSession(usuarioSelected.getSessionId());
                usuarios=usuarioService.getAll();
                agregarSession(0);
                usuarioDataModel=new UsuarioDataModel(usuarios);
                msgControler.addInfo("Información:", "Se ha eliminado la sessión:"+usuarioSelected.getSessionId());
    	} 
        
    }
    public void borra() {
        
        
       
    	if(usuarioSelected !=null && usuarioSelected.getIdUsuario()>0) {
    		if(msgDebug) System.out.println("getIdUsuario():"+usuarioSelected.getIdUsuario());
                usuarioService.borra(usuarioSelected);
                usuarios = usuarioService.getAll();
                usuarioDataModel=new UsuarioDataModel(usuarios);
                msgControler.addInfo("Información:", "se ha eliminado al usuario");
    	} 
    	
    }

    private void agregarSession(int op) {
         List<Usuario> usuarios2=new ArrayList<Usuario>();
        for(Usuario u: this.usuarios){
            
            //System.out.println("Usuarios conectados");
    
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            if (principal instanceof Usuario) {
                Usuario userDetails = (Usuario) principal;
                
                if (userDetails.getUsername().equals(u.getUsername())) {
                    for (SessionInformation information : sessionRegistry.getAllSessions(userDetails, true)) {
                        //System.out.println("SessionInformation:"+information);
                        if(op==0 && !information.isExpired()){
                        u.setSessionId(information.getSessionId());
                        u.setSessionLastRequest(information.getLastRequest());
                        u.setSessionExpired(information.isExpired());
                        }
                    }
                }
                
            }
        }
        usuarios2.add(u);
        }
        this.usuarios=usuarios2;
        usuarioDataModel=new UsuarioDataModel(usuarios);
    
    }
    
    private void eliminarSession(String idsession) {
        
        for(Usuario u: this.usuarios){
            
    
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            if (principal instanceof Usuario) {
                Usuario userDetails = (Usuario) principal;
                
//                if (userDetails.getUsername().equals(userName)) {
                    for (SessionInformation information : sessionRegistry.getAllSessions(userDetails, true)) {
                        if(information.getSessionId().equalsIgnoreCase(idsession)){
                        //System.out.println("SessionInformation:"+information);
                        information.expireNow();
                        }
                    }
//                }
            }
        }
        }
    
    }
    
    public void onRowSelectUsuarioData(SelectEvent event) {

        Usuario od=((Usuario) event.getObject());
        this.usuarioSelected=od;
        
        msgControler.addInfo("Información", "se selecciono el usuario:"+od.getUsername() );
        
        

    }
        
}
