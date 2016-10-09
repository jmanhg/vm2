package mx.com.oposicion.service;

import java.util.List;

import mx.com.oposicion.model.Reporte;

public interface ReporteService {
	List<Reporte> getAll();
	void inserta(Reporte c);
	void actualiza(Reporte c);
	void borra(Reporte c);
	Reporte encuentra(int id);
        List<Reporte> encuentraByIdUsuario(int id);
}
