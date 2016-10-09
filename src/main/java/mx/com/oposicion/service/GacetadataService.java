package mx.com.oposicion.service;

import java.util.Date;
import java.util.List;

import mx.com.oposicion.model.Gacetadata;

public interface GacetadataService {
	List<Gacetadata> getAll();
	void inserta(Gacetadata c);
	void actualiza(Gacetadata c);
	void borra(Gacetadata c);
	Gacetadata encuentra(int id);
        Gacetadata encuentraBlob(int id);
        List<Gacetadata> encuentraPor(Gacetadata c);
        
        List<Gacetadata> getAllV();
	void insertaV(Gacetadata c);
	void actualizaV(Gacetadata c);
	void borraV(Gacetadata c);
	Gacetadata encuentraV(int id);
        Gacetadata encuentraBlobV(int id);
        List<Gacetadata> encuentraPorV(Gacetadata c);
        
        List<Gacetadata> findFigurativo(int solicitud,Date fechaPub1,Date fechaPub2, int limit);
        List<Gacetadata> findFigurativoClase(int solicitud,int clase,Date fechaPub1,Date fechaPub2, int limit);
}
