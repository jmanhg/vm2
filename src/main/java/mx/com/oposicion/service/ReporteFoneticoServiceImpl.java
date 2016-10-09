package mx.com.oposicion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.oposicion.model.ReporteFonetico;
import mx.com.oposicion.persistence.ReporteFoneticoMapper;

@Service
public class ReporteFoneticoServiceImpl implements ReporteFoneticoService {
	
	@Autowired
	private ReporteFoneticoMapper reporteFoneticoMapper;

	@Override
	public List<ReporteFonetico> getAll() {
		return reporteFoneticoMapper.getAll();
	}
	
	@Override
	public void inserta(ReporteFonetico p) {

		reporteFoneticoMapper.insert(p);
	}

	@Override
	public void actualiza(ReporteFonetico p) {

		reporteFoneticoMapper.update(p);
	}
	
	@Override
	public void borra(ReporteFonetico p) {

		reporteFoneticoMapper.delete(p);
	}
	
	@Override
	public List<ReporteFonetico> encuentra(int id) {
		return reporteFoneticoMapper.findById(id);
	}
}
