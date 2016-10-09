package mx.com.oposicion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.oposicion.model.GacetadataViena;
import mx.com.oposicion.persistence.GacetadataVienaMapper;

@Service
public class GacetadataVienaServiceImpl implements GacetadataVienaService {
	
	@Autowired
	private GacetadataVienaMapper GacetadataVienaMapper;

	@Override
	public List<GacetadataViena> getAll() {
		return GacetadataVienaMapper.getAll();
	}
	
	@Override
	public List<GacetadataViena> encuentra(int key) {
		return GacetadataVienaMapper.findById( key);
	}
        
        @Override
	public List<GacetadataViena> encuentraSolicitud(String solicitud) {
		return GacetadataVienaMapper.findBySolicitud( solicitud);
	}
        
        @Override
	public List<GacetadataViena> encuentraByGac(GacetadataViena g) {
		return GacetadataVienaMapper.findByGac(g);
	}

    @Override
    public Integer selectMaxSecuencia(String solicitud) {
        return GacetadataVienaMapper.selectMaxSecuencia(solicitud);
    }

    @Override
    public int insert(GacetadataViena gacetadataViena) {
        return GacetadataVienaMapper.insert(gacetadataViena);
    }
    @Override
    public int delete(GacetadataViena gacetadataViena) {
        return GacetadataVienaMapper.delete(gacetadataViena);
    }

    @Override
    public String findCodVienaBySolicitud(String solicitud) {
        return GacetadataVienaMapper.findCodVienaBySolicitud(solicitud);
    }
        
    @Override
	public List<GacetadataViena> getAllV() {
		return GacetadataVienaMapper.getAllV();
	}
	
	@Override
	public List<GacetadataViena> encuentraV(int key) {
		return GacetadataVienaMapper.findByIdV( key);
	}
        
        @Override
	public List<GacetadataViena> encuentraSolicitudV(String solicitud) {
		return GacetadataVienaMapper.findBySolicitudV( solicitud);
	}
        
        @Override
	public List<GacetadataViena> encuentraByGacV(GacetadataViena g) {
		return GacetadataVienaMapper.findByGacV(g);
	}

    @Override
    public Integer selectMaxSecuenciaV(String solicitud) {
        return GacetadataVienaMapper.selectMaxSecuenciaV(solicitud);
    }

    @Override
    public int insertV(GacetadataViena gacetadataViena) {
        return GacetadataVienaMapper.insertV(gacetadataViena);
    }
    @Override
    public int deleteV(GacetadataViena gacetadataViena) {
        return GacetadataVienaMapper.deleteV(gacetadataViena);
    }
    @Override
    public String findCodVienaBySolicitudV(String solicitud) {
        return GacetadataVienaMapper.findCodVienaBySolicitudV(solicitud);
    }
}
