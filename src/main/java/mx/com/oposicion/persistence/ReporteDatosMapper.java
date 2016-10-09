package mx.com.oposicion.persistence;

import java.util.List;
import mx.com.oposicion.model.ReporteDatos;

public interface ReporteDatosMapper {
	void insert(ReporteDatos reporteDatos);
	void update(ReporteDatos reporteDatos);
	void delete(ReporteDatos reporteDatos);
	List<ReporteDatos> findById(int id);
	List<ReporteDatos> getAll();
}
