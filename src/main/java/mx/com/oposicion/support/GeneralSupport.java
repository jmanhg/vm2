package mx.com.oposicion.support;

import javax.faces.context.FacesContext;

import org.springframework.security.core.context.SecurityContextHolder;


public class GeneralSupport {
	
	public static String getCurrentUser(){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return username;
	}
	
	public static String getLoginUser(){
		String username = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("j_username");
		return username;
	}
	
	public static String getPivote(){
		String username = "C2K";
		return username;
	}
    
}
