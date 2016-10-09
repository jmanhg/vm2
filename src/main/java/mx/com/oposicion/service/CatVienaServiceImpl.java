package mx.com.oposicion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.oposicion.model.CatViena;
import mx.com.oposicion.persistence.CatVienaMapper;

@Service
public class CatVienaServiceImpl implements CatVienaService {
	
	@Autowired
	private CatVienaMapper catVienaMapper;

	@Override
	public List<CatViena> getAll() {
		return catVienaMapper.getAll();
	}
	
	@Override
	public CatViena encuentra(int key) {
		return catVienaMapper.findById( key);
	}
        
        @Override
	public CatViena encuentraByCod(int categoria,int division, int seccion) {
		return catVienaMapper.findByIdCod(categoria, division,  seccion);
	}
}
