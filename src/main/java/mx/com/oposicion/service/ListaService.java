package mx.com.oposicion.service;

import java.util.List;

import mx.com.oposicion.model.Lista;

public interface ListaService {
	List<Lista> getAll();
	void inserta(Lista c);
	void actualiza(Lista c);
	void borra(Lista c);
	Lista encuentra(int id);
        List<Lista> encuentraByIdUsuario(int id);
}
