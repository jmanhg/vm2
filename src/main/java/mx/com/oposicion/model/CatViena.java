package mx.com.oposicion.model;

import java.io.Serializable;

public class CatViena implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int categoria;
    private int division;
    private int seccion;
    private String descripcion;
    private int idkey;

    public CatViena() {
    }

    public CatViena(int categoria, int division, int seccion, String descripcion, int idkey) {
        this.categoria = categoria;
        this.division = division;
        this.seccion = seccion;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdkey() {
        return idkey;
    }

    public void setIdkey(int idkey) {
        this.idkey = idkey;
    }

   

}
