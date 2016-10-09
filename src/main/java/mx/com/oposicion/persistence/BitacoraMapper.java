package mx.com.oposicion.persistence;

import mx.com.oposicion.model.Bitacora;
import mx.com.oposicion.model.DateWrapper;

public interface BitacoraMapper {
	void insert(Bitacora bitacora);
	void clean(DateWrapper dw);
}
