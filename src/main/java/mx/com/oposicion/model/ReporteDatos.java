package mx.com.oposicion.model;

import java.io.Serializable;
import java.util.Date;

public class ReporteDatos implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int idReg;
    private int idRep;
    private int idRegistro;
    private String tipo;
    private String subtipo;
    private String registro;
    private String solicitud;
    private String denominacion;
    private String clase;
    private String titular;
    private String apoderado;
    private int resultados;
    private byte[] imagen;
    private byte[] fonetico;
    private byte[] ejecutivo;
    private byte[] figurativo;
    private byte[] figurativo2;

    public ReporteDatos() {
    }

    public ReporteDatos(int idReg, int idRep, int idRegistro, String tipo, String subtipo, String registro, String solicitud, String denominacion, String clase, String titular, String apoderado, int resultados, byte[] imagen, byte[] fonetico, byte[] ejecutivo, byte[] figurativo, byte[] figurativo2) {
        this.idReg = idReg;
        this.idRep = idRep;
        this.idRegistro = idRegistro;
        this.tipo = tipo;
        this.subtipo = subtipo;
        this.registro = registro;
        this.solicitud = solicitud;
        this.denominacion = denominacion;
        this.clase = clase;
        this.titular = titular;
        this.apoderado = apoderado;
        this.resultados = resultados;
        this.imagen = imagen;
        this.fonetico = fonetico;
        this.ejecutivo = ejecutivo;
        this.figurativo = figurativo;
        this.figurativo2 = figurativo2;
    }

    public int getIdReg() {
        return idReg;
    }

    public void setIdReg(int idReg) {
        this.idReg = idReg;
    }

    public int getIdRep() {
        return idRep;
    }

    public void setIdRep(int idRep) {
        this.idRep = idRep;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSubtipo() {
        return subtipo;
    }

    public void setSubtipo(String subtipo) {
        this.subtipo = subtipo;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getApoderado() {
        return apoderado;
    }

    public void setApoderado(String apoderado) {
        this.apoderado = apoderado;
    }

    public int getResultados() {
        return resultados;
    }

    public void setResultados(int resultados) {
        this.resultados = resultados;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public byte[] getFonetico() {
        return fonetico;
    }

    public void setFonetico(byte[] fonetico) {
        this.fonetico = fonetico;
    }

    public byte[] getEjecutivo() {
        return ejecutivo;
    }

    public void setEjecutivo(byte[] ejecutivo) {
        this.ejecutivo = ejecutivo;
    }

    public byte[] getFigurativo() {
        return figurativo;
    }

    public void setFigurativo(byte[] figurativo) {
        this.figurativo = figurativo;
    }

    public byte[] getFigurativo2() {
        return figurativo2;
    }

    public void setFigurativo2(byte[] figurativo2) {
        this.figurativo2 = figurativo2;
    }


}
