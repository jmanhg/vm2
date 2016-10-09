package mx.com.oposicion.persistence;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import mx.com.oposicion.model.Usuario;



public interface UsuarioMapper {

	UserDetails getUsuarioByName(String username);
	List<GrantedAuthority> getPerfiles(String usuario);
	void updateIntentosFallidos(Usuario user);
	void bloquearUsuario(Usuario user);
	void desBloquearUsuario(Usuario user);
        
        
        void insert(Usuario usuario);
	void update(Usuario usuario);
	void delete(Usuario usuario);
	Usuario findById(int id);
	Usuario findByIdClave(Usuario usuario);
	List<Usuario> getAll();
	
}
