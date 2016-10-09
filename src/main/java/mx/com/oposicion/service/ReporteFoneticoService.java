package mx.com.oposicion.service;

import java.util.List;

import mx.com.oposicion.model.ReporteFonetico;

public interface ReporteFoneticoService {
	List<ReporteFonetico> getAll();
	void inserta(ReporteFonetico c);
	void actualiza(ReporteFonetico c);
	void borra(ReporteFonetico c);
	List<ReporteFonetico> encuentra(int id);
}
