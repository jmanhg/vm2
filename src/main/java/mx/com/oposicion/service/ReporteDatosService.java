package mx.com.oposicion.service;

import java.util.List;

import mx.com.oposicion.model.ReporteDatos;

public interface ReporteDatosService {
	List<ReporteDatos> getAll();
	void inserta(ReporteDatos c);
	void actualiza(ReporteDatos c);
	void borra(ReporteDatos c);
	List<ReporteDatos> encuentra(int id);
}
