package mx.com.oposicion.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.oposicion.model.Gacetadata;
import mx.com.oposicion.persistence.GacetadataMapper;

@Service
public class GacetadataServiceImpl implements GacetadataService {
	
	@Autowired
	private GacetadataMapper gacetadataMapper;

	@Override
	public List<Gacetadata> getAll() {
		return gacetadataMapper.getAll();
	}
	
	@Override
	public void inserta(Gacetadata p) {

		gacetadataMapper.insert(p);
	}

	@Override
	public void actualiza(Gacetadata p) {

		gacetadataMapper.update(p);
	}
	
	@Override
	public void borra(Gacetadata p) {

		gacetadataMapper.delete(p);
	}
	
	@Override
	public Gacetadata encuentra(int id) {
		return gacetadataMapper.findById(id);
	}
        
        @Override
	public Gacetadata encuentraBlob(int id) {
		return gacetadataMapper.findByIdBlob(id);
	}

        @Override
        public List<Gacetadata> encuentraPor(Gacetadata c) {
            return gacetadataMapper.findBy(c);
        }
        
        
        @Override
	public List<Gacetadata> getAllV() {
		return gacetadataMapper.getAllV();
	}
	
	@Override
	public void insertaV(Gacetadata p) {

		gacetadataMapper.insertV(p);
	}

	@Override
	public void actualizaV(Gacetadata p) {

		gacetadataMapper.updateV(p);
	}
	
	@Override
	public void borraV(Gacetadata p) {

		gacetadataMapper.deleteV(p);
	}
	
	@Override
	public Gacetadata encuentraV(int id) {
		return gacetadataMapper.findByIdV(id);
	}
        
        @Override
	public Gacetadata encuentraBlobV(int id) {
		return gacetadataMapper.findByIdBlobV(id);
	}

        @Override
        public List<Gacetadata> encuentraPorV(Gacetadata c) {
            return gacetadataMapper.findByV(c);
        }

    @Override
    public List<Gacetadata> findFigurativo(int solicitud,Date fechaPub1,Date fechaPub2,int limit) {
        return gacetadataMapper.findFigurativo(solicitud,fechaPub1,fechaPub2,limit);
    }
    
    @Override
    public List<Gacetadata> findFigurativoClase(int solicitud,int clase,Date fechaPub1,Date fechaPub2, int limit) {
        return gacetadataMapper.findFigurativoClase(solicitud,clase,fechaPub1,fechaPub2,limit);
    }
        
}
