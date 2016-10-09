package mx.com.oposicion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.oposicion.model.Reporte;
import mx.com.oposicion.persistence.ReporteMapper;

@Service
public class ReporteServiceImpl implements ReporteService {
	
	@Autowired
	private ReporteMapper reporteMapper;

	@Override
	public List<Reporte> getAll() {
		return reporteMapper.getAll();
	}
	
	@Override
	public void inserta(Reporte p) {

		reporteMapper.insert(p);
	}

	@Override
	public void actualiza(Reporte p) {

		reporteMapper.update(p);
	}
	
	@Override
	public void borra(Reporte p) {

		reporteMapper.delete(p);
	}
	
	@Override
	public Reporte encuentra(int id) {
		return reporteMapper.findById(id);
	}

    @Override
    public List<Reporte> encuentraByIdUsuario(int id) {
        return reporteMapper.findByIdUsuario(id);
    }
        
        
}
