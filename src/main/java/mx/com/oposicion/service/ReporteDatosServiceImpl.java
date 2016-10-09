package mx.com.oposicion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.oposicion.model.ReporteDatos;
import mx.com.oposicion.persistence.ReporteDatosMapper;

@Service
public class ReporteDatosServiceImpl implements ReporteDatosService {
	
	@Autowired
	private ReporteDatosMapper reporteDatosMapper;

	@Override
	public List<ReporteDatos> getAll() {
		return reporteDatosMapper.getAll();
	}
	
	@Override
	public void inserta(ReporteDatos p) {

		reporteDatosMapper.insert(p);
	}

	@Override
	public void actualiza(ReporteDatos p) {

		reporteDatosMapper.update(p);
	}
	
	@Override
	public void borra(ReporteDatos p) {

		reporteDatosMapper.delete(p);
	}
	
	@Override
	public List<ReporteDatos> encuentra(int id) {
		return reporteDatosMapper.findById(id);
	}
}
