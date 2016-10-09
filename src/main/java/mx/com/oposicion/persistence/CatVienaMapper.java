package mx.com.oposicion.persistence;

import java.util.List;
import mx.com.oposicion.model.CatViena;
import org.apache.ibatis.annotations.Param;

public interface CatVienaMapper {
	CatViena findByIdCod(@Param("categoria")int categoria,@Param("division")int division,@Param("seccion")int seccion);
	CatViena findById(int key);
	List<CatViena> getAll();
}
