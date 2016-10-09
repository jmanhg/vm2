package mx.com.oposicion.persistence;

import java.util.List;
import mx.com.oposicion.model.ReporteFonetico;

public interface ReporteFoneticoMapper {
	void insert(ReporteFonetico reporteFonetico);
	void update(ReporteFonetico reporteFonetico);
	void delete(ReporteFonetico reporteFonetico);
	List<ReporteFonetico> findById(int id);
	List<ReporteFonetico> getAll();
}
