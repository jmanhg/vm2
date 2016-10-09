package mx.com.oposicion.persistence;

import java.util.List;
import mx.com.oposicion.model.GacetadataViena;
import org.apache.ibatis.annotations.Param;

public interface GacetadataVienaMapper {
	List<GacetadataViena> findByGac(GacetadataViena g);
	List<GacetadataViena> findById(int key);
	List<GacetadataViena> findBySolicitud(String solicitud);
	List<GacetadataViena> getAll();
        Integer selectMaxSecuencia (String solicitud);
        int insert(GacetadataViena gacetadataViena);
        int delete(GacetadataViena gacetadataViena);
        String findCodVienaBySolicitud(String solicitud);
        
        List<GacetadataViena> findByGacV(GacetadataViena g);
	List<GacetadataViena> findByIdV(int key);
	List<GacetadataViena> findBySolicitudV(String solicitud);
	List<GacetadataViena> getAllV();
        Integer selectMaxSecuenciaV (String solicitud);
        int insertV(GacetadataViena gacetadataViena);
        int deleteV(GacetadataViena gacetadataViena);
        String findCodVienaBySolicitudV(String solicitud);
}
