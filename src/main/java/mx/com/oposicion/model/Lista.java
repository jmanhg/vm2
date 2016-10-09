package mx.com.oposicion.model;

import java.io.Serializable;
import java.util.Date;

public class Lista implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int idlista;
    private String nombre;
    private Date fecha;
    private int activo;
    private int idusuario;

    public Lista() {
    }

    public Lista(int idlista, String nombre, Date fecha, int activo,int idusuario) {
        this.idlista = idlista;
        this.nombre = nombre;
        this.fecha = fecha;
        this.activo = activo;
        this.idusuario = idusuario;
    }

    public int getIdlista() {
        return idlista;
    }

    public void setIdlista(int idlista) {
        this.idlista = idlista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

  
   

}
