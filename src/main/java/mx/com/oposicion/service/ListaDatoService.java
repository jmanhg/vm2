package mx.com.oposicion.service;

import java.util.List;

import mx.com.oposicion.model.ListaDato;

public interface ListaDatoService {
	void inserta(ListaDato c);
	void borra(ListaDato c);
	List<ListaDato> encuentra(int id);
}
