package mx.com.oposicion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.oposicion.model.ListaDato;
import mx.com.oposicion.persistence.ListaDatoMapper;

@Service
public class ListaDatoServiceImpl implements ListaDatoService {
	
	@Autowired
	private ListaDatoMapper listaDatoMapper;

	
	
	@Override
	public void inserta(ListaDato p) {

		listaDatoMapper.insert(p);
	}

	
	
	@Override
	public void borra(ListaDato p) {

		listaDatoMapper.delete(p);
	}
	
	@Override
	public List<ListaDato> encuentra(int id) {
		return listaDatoMapper.findById(id);
	}
        
}
