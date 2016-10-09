package mx.com.oposicion.model;

import java.io.Serializable;
import java.util.Date;

public class Reporte implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int idRep;
    private String nombre;
    private Date fecha;
    private int tipo;
    private int numRegs;
    private int activo;
    private int idusuario;

    public Reporte() {
    }

    public Reporte(int idRep, String nombre, Date fecha, int tipo,int numRegs, int activo,int idusuario) {
        this.idRep = idRep;
        this.nombre = nombre;
        this.fecha = fecha;
        this.tipo = tipo;
        this.numRegs = numRegs;
        this.activo = activo;
        this.idusuario=idusuario;
    }

    public int getIdRep() {
        return idRep;
    }

    public void setIdRep(int idRep) {
        this.idRep = idRep;
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

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getNumRegs() {
        return numRegs;
    }

    public void setNumRegs(int numRegs) {
        this.numRegs = numRegs;
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
