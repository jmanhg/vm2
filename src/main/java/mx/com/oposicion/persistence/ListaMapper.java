package mx.com.oposicion.persistence;

import java.util.List;
import mx.com.oposicion.model.Lista;

public interface ListaMapper {
	void insert(Lista lista);
	void update(Lista lista);
	void delete(Lista lista);
	Lista findById(int id);
	List<Lista> getAll();
        List<Lista> findByIdUsuario(int id);
}
