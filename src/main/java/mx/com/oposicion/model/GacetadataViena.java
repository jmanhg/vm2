package mx.com.oposicion.model;

import java.io.Serializable;

public class GacetadataViena implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String solicitud;
    private int idkey;
    private int categoria;
    private int division;
    private int seccion;
    private int secuencia;
    

    public GacetadataViena() {
    }

    public GacetadataViena(String solicitud, int idkey, int categoria, int division, int seccion, int secuencia) {
        this.solicitud = solicitud;
        this.idkey = idkey;
        this.categoria = categoria;
        this.division = division;
        this.seccion = seccion;
        this.secuencia = secuencia;
    }

    public String getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }

    public int getIdkey() {
        return idkey;
    }

    public void setIdkey(int idkey) {
        this.idkey = idkey;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getDivision() {
        return division;
    }

    public void setDivision(int division) {
        this.division = division;
    }

    public int getSeccion() {
        return seccion;
    }

    public void setSeccion(int seccion) {
        this.seccion = seccion;
    }

    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

}
