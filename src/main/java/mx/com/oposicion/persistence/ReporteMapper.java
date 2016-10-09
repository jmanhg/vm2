package mx.com.oposicion.persistence;

import java.util.List;
import mx.com.oposicion.model.Reporte;

public interface ReporteMapper {
	void insert(Reporte reporte);
	void update(Reporte reporte);
	void delete(Reporte reporte);
	Reporte findById(int id);
	List<Reporte> getAll();
        List<Reporte> findByIdUsuario(int id);
}
