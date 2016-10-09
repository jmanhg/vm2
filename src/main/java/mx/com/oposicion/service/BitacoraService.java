package mx.com.oposicion.service;

import java.io.Serializable;

public interface BitacoraService extends Serializable {
	void clean();
	void insert(int action, String extraInfo);
}
