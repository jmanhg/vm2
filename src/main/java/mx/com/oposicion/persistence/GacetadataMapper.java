package mx.com.oposicion.persistence;

import java.util.Date;
import java.util.List;
import mx.com.oposicion.model.Gacetadata;
import org.apache.ibatis.annotations.Param;

public interface GacetadataMapper {
	void insert(Gacetadata gacetadata);
	void update(Gacetadata gacetadata);
	void delete(Gacetadata gacetadata);
	Gacetadata findById(int id);
        Gacetadata findByIdBlob(int id);
	List<Gacetadata> getAll();
        List<Gacetadata> findBy(Gacetadata gacetadata);
        
        void insertV(Gacetadata gacetadata);
	void updateV(Gacetadata gacetadata);
	void deleteV(Gacetadata gacetadata);
	Gacetadata findByIdV(int id);
        Gacetadata findByIdBlobV(int id);
	List<Gacetadata> getAllV();
        List<Gacetadata> findByV(Gacetadata gacetadata);
        
        List<Gacetadata> findFigurativo(@Param("solicitud")int solicitud,@Param("fechaPub1")Date fechaPub1,@Param("fechaPub2")Date fechaPub2,@Param("limit")int limit);
        List<Gacetadata> findFigurativoClase(@Param("solicitud")int solicitud,@Param("clase")int clase,@Param("fechaPub1")Date fechaPub1,@Param("fechaPub2")Date fechaPub2,@Param("limit")int limit);
        
}
