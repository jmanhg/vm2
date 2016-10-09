package mx.com.oposicion.persistence;


import java.sql.Timestamp;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import mx.com.oposicion.model.Perfil;
import mx.com.oposicion.model.Usuario;

//@Service("usuarioMapper")
public class UsuarioMapperImpl implements UsuarioMapper{

	@Override
    public UserDetails getUsuarioByName(String username) {
		Usuario ud = new Usuario();

		return ud;
    }

	@Override
    public List<GrantedAuthority> getPerfiles(String usuario) {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
	public void updateIntentosFallidos(Usuario user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bloquearUsuario(Usuario user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void desBloquearUsuario(Usuario user) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void insert(Usuario usuario) {
        }

    @Override
    public void update(Usuario usuario) {
        }

    @Override
    public void delete(Usuario usuario) {
        }

    @Override
    public Usuario findById(int id) {
        Usuario ud = new Usuario();
        return ud;
    }
    
    @Override
    public Usuario findByIdClave(Usuario usuario) {
        Usuario ud = new Usuario();
        return ud;
    }

    @Override
    public List<Usuario> getAll() {
        return null;
        }

}
