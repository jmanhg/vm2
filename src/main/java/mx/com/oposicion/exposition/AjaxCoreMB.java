package mx.com.oposicion.exposition;

import java.io.Serializable;

import org.springframework.stereotype.Service;

@Service
public class AjaxCoreMB implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**variable para update**/
	private String nombre;
	/**variable para poll**/
	private int numero;
	/**variable para eventos keyup y blur**/
	private String username;
	
	public void incrementar(){
		numero++;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	

}
