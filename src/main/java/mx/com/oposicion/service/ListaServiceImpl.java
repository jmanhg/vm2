package mx.com.oposicion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.oposicion.model.Lista;
import mx.com.oposicion.persistence.ListaMapper;

@Service
public class ListaServiceImpl implements ListaService {
	
	@Autowired
	private ListaMapper listaMapper;

	@Override
	public List<Lista> getAll() {
		return listaMapper.getAll();
	}
	
	@Override
	public void inserta(Lista p) {

		listaMapper.insert(p);
	}

	@Override
	public void actualiza(Lista p) {

		listaMapper.update(p);
	}
	
	@Override
	public void borra(Lista p) {

		listaMapper.delete(p);
	}
	
	@Override
	public Lista encuentra(int id) {
		return listaMapper.findById(id);
	}
        
        @Override
    public List<Lista> encuentraByIdUsuario(int id) {
        return listaMapper.findByIdUsuario(id);
    }
}
