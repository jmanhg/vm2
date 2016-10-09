package mx.com.oposicion.service;

import java.util.List;

import mx.com.oposicion.model.GacetadataViena;

public interface GacetadataVienaService {
	List<GacetadataViena> getAll();
	List<GacetadataViena> encuentra(int key);
        List<GacetadataViena> encuentraSolicitud(String solicitud);
        List<GacetadataViena> encuentraByGac(GacetadataViena g);
        Integer selectMaxSecuencia (String solicitud);
        int insert(GacetadataViena gacetadataViena);
        int delete(GacetadataViena gacetadataViena);
        String findCodVienaBySolicitud(String solicitud);
        
        List<GacetadataViena> getAllV();
	List<GacetadataViena> encuentraV(int key);
        List<GacetadataViena> encuentraSolicitudV(String solicitud);
        List<GacetadataViena> encuentraByGacV(GacetadataViena g);
        Integer selectMaxSecuenciaV (String solicitud);
        int insertV(GacetadataViena gacetadataViena);
        int deleteV(GacetadataViena gacetadataViena);
        String findCodVienaBySolicitudV(String solicitud);
}
