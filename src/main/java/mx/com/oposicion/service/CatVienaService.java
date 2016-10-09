package mx.com.oposicion.service;

import java.util.List;

import mx.com.oposicion.model.CatViena;

public interface CatVienaService {
	List<CatViena> getAll();
	CatViena encuentra(int key);
        CatViena encuentraByCod(int categoria,int division, int seccion);
}
