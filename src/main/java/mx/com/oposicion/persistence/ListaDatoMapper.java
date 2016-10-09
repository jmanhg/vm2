package mx.com.oposicion.persistence;

import java.util.List;
import mx.com.oposicion.model.ListaDato;

public interface ListaDatoMapper {
	void insert(ListaDato listaDato);
	void delete(ListaDato listaDato);
	List<ListaDato> findById(int id);
}
