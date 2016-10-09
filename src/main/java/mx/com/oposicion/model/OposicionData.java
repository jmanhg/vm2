/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.oposicion.model;

import fonetic.Gacetadata;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author juan
 */
public class OposicionData  implements Serializable {
    
    private int idRow;
    private int idRegistro;
    private String tipo;
    private String subtipo;
    private String registro;
    private String solicitud;
    private String denominacion;
    private String clases;
    private String titular;
    private String apoderado;
    private int numResultados;
    
    private byte[] reporte;
    private byte[] reporteEjecutivo;
    private byte[] reporteFigurativo;
    private byte[] reporteFigurativo2;
    private List<fonetic.Gacetadata> fonetico;
    private List<mx.com.oposicion.model.Gacetadata> figurativo;
    private String msg;
    private int diseno;

    public OposicionData() {
    }

    public OposicionData(int idRow, String denominacion, String clases, String titular, int numResultados, byte[] reporte, List<Gacetadata> fonetico,String msg) {
        this.idRow = idRow;
        this.denominacion = denominacion;
        this.clases = clases;
        this.titular = titular;
        this.numResultados = numResultados;
        this.reporte = reporte;
        this.fonetico = fonetico;
        this.msg=msg;
    }

    public OposicionData(int idRow, int idRegistro,String tipo, String subtipo,String registro, String solicitud, String denominacion, String clases, String titular, String apoderado, int numResultados, byte[] reporte, byte[] reporteEjecutivo, byte[] reporteFigurativo, byte[] reporteFigurativo2, List<Gacetadata> fonetico,List<mx.com.oposicion.model.Gacetadata> figurativo, String msg,int diseno) {
        this.idRow = idRow;
        this.idRegistro = idRegistro;
        this.tipo = tipo;
        this.subtipo = subtipo;
        this.registro = registro;
        this.solicitud = solicitud;
        this.denominacion = denominacion;
        this.clases = clases;
        this.titular = titular;
        this.apoderado = apoderado;
        this.numResultados = numResultados;
        this.reporte = reporte;
        this.reporteEjecutivo = reporteEjecutivo;
        this.reporteFigurativo = reporteFigurativo;
        this.reporteFigurativo2 = reporteFigurativo2;
        this.fonetico = fonetico;
        this.figurativo = figurativo;
        this.msg = msg;
        this.diseno=diseno;
    }

    public int getIdRow() {
        return idRow;
    }

    public void setIdRow(int idRow) {
        this.idRow = idRow;
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

    public String getClases() {
        return clases;
    }

    public void setClases(String clases) {
        this.clases = clases;
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

    public int getNumResultados() {
        return numResultados;
    }

    public void setNumResultados(int numResultados) {
        this.numResultados = numResultados;
    }

    public byte[] getReporte() {
        return reporte;
    }

    public void setReporte(byte[] reporte) {
        this.reporte = reporte;
    }

    public byte[] getReporteEjecutivo() {
        return reporteEjecutivo;
    }

    public void setReporteEjecutivo(byte[] reporteEjecutivo) {
        this.reporteEjecutivo = reporteEjecutivo;
    }

    public byte[] getReporteFigurativo() {
        return reporteFigurativo;
    }

    public void setReporteFigurativo(byte[] reporteFigurativo) {
        this.reporteFigurativo = reporteFigurativo;
    }

    public byte[] getReporteFigurativo2() {
        return reporteFigurativo2;
    }

    public void setReporteFigurativo2(byte[] reporteFigurativo2) {
        this.reporteFigurativo2 = reporteFigurativo2;
    }

    public List<Gacetadata> getFonetico() {
        return fonetico;
    }

    public void setFonetico(List<Gacetadata> fonetico) {
        this.fonetico = fonetico;
    }

    public List<mx.com.oposicion.model.Gacetadata> getFigurativo() {
        return figurativo;
    }

    public void setFigurativo(List<mx.com.oposicion.model.Gacetadata> figurativo) {
        this.figurativo = figurativo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getDiseno() {
        return diseno;
    }

    public void setDiseno(int diseno) {
        this.diseno = diseno;
    }
    
    
}
