package mx.com.oposicion.service;

import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.AuthenticationException;

import mx.com.oposicion.model.Usuario;

public interface UsuarioService {

	UserDetails loadUserByUsername(String username) 
                throws UsernameNotFoundException, DataAccessException,AuthenticationException;
	Usuario getUsuario();
	void updateIntentosFallidos(Usuario user);
	void bloquearUsuario(Usuario user);
	void desBloquearUsuario(Usuario user);
        
        List<Usuario> getAll();
	void inserta(Usuario c);
	void actualiza(Usuario c);
	void borra(Usuario c);
	Usuario encuentra(int id);
        Usuario findByIdClave(Usuario c);
}
