package mx.com.oposicion.model;

import org.springframework.security.core.GrantedAuthority;

public class Perfil implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	private String auth;
	
	public Perfil() {
    }
	
	public Perfil(String authority) {
		this.auth = authority;
    }

	@Override
    public String getAuthority() {
	    return auth;
    }
	
    public String setAuthority(String authority) {
	    return auth = authority;
    }

}
