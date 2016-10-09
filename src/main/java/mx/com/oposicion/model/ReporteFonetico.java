package mx.com.oposicion.model;

import java.io.Serializable;
import java.util.Date;

public class ReporteFonetico implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int idGac;
    private int idReg;
    private int idRegistro;
    private String tipo;
    private String subtipo;
    private String registro;
    private String solicitud;
    private String denominacion;
    private String clase;
    private String titular;
    private String apoderado;
    private String gaceta;
    private String viena;
    private double coeficiente;
    private int ocurrencia;

    public ReporteFonetico() {
    }

    public ReporteFonetico(int idGac, int idReg, int idRegistro, String tipo, String subtipo, String registro, String solicitud, String denominacion, String clase, String titular, String apoderado, String gaceta, String viena, double coeficiente, int ocurrencia) {
        this.idGac = idGac;
        this.idReg = idReg;
        this.idRegistro = idRegistro;
        this.tipo = tipo;
        this.subtipo = subtipo;
        this.registro = registro;
        this.solicitud = solicitud;
        this.denominacion = denominacion;
        this.clase = clase;
        this.titular = titular;
        this.apoderado = apoderado;
        this.gaceta = gaceta;
        this.viena = viena;
        this.coeficiente = coeficiente;
        this.ocurrencia = ocurrencia;
    }

    public int getIdGac() {
        return idGac;
    }

    public void setIdGac(int idGac) {
        this.idGac = idGac;
    }

    public int getIdReg() {
        return idReg;
    }

    public void setIdReg(int idReg) {
        this.idReg = idReg;
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

    public String getGaceta() {
        return gaceta;
    }

    public void setGaceta(String gaceta) {
        this.gaceta = gaceta;
    }

    public String getViena() {
        return viena;
    }

    public void setViena(String viena) {
        this.viena = viena;
    }

    public double getCoeficiente() {
        return coeficiente;
    }

    public void setCoeficiente(double coeficiente) {
        this.coeficiente = coeficiente;
    }

    public int getOcurrencia() {
        return ocurrencia;
    }

    public void setOcurrencia(int ocurrencia) {
        this.ocurrencia = ocurrencia;
    }

}
