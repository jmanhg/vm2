package mx.com.oposicion.model;

import java.io.Serializable;
import java.util.Date;

public class ListaDato implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int idlista;
    private int idregistro;

    public ListaDato() {
    }

    public ListaDato(int idlista, int idregistro) {
        this.idlista = idlista;
        this.idregistro = idregistro;
    }

    public int getIdlista() {
        return idlista;
    }

    public void setIdlista(int idlista) {
        this.idlista = idlista;
    }

    public int getIdregistro() {
        return idregistro;
    }

    public void setIdregistro(int idregistro) {
        this.idregistro = idregistro;
    }
    
}