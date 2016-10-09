package mx.com.oposicion.model;

import java.io.Serializable;
import java.util.Date;

public class Gacetadata implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int  idRegistro;
    private String tipo;
    private String subtipo;
    private String registro;
    private String solicitud;
    private String titulo;
    private String titular;
    private String gaceta;
    private int pagina;
    private String clase;
    private String prioridad;
    private String idPublPCT;
    private String idSolicitudPCT;
    private String pais;
    private Date presentacion;
    private Date fRegistro;
    private Date vigencia;
    private String tipoGaceta;
    private String apoderado;
    private String diseno;
    private String titularDir;
    private String presentacion2;
    private String fRegistro2;
    private byte[] imagen;

    public Gacetadata() {
    }

    public Gacetadata(int idRegistro, String tipo, String subtipo, String registro, String solicitud, String titulo, String titular, String gaceta, int pagina, String clase, String prioridad, String idPublPCT, String idSolicitudPCT, String pais, Date presentacion, Date fRegistro, Date vigencia, String tipoGaceta, String apoderado, String diseno, String titularDir, String presentacion2, String fRegistro2, byte[] imagen) {
        this.idRegistro = idRegistro;
        this.tipo = tipo;
        this.subtipo = subtipo;
        this.registro = registro;
        this.solicitud = solicitud;
        this.titulo = titulo;
        this.titular = titular;
        this.gaceta = gaceta;
        this.pagina = pagina;
        this.clase = clase;
        this.prioridad = prioridad;
        this.idPublPCT = idPublPCT;
        this.idSolicitudPCT = idSolicitudPCT;
        this.pais = pais;
        this.presentacion = presentacion;
        this.fRegistro = fRegistro;
        this.vigencia = vigencia;
        this.tipoGaceta = tipoGaceta;
        this.apoderado = apoderado;
        this.diseno = diseno;
        this.titularDir = titularDir;
        this.presentacion2 = presentacion2;
        this.fRegistro2 = fRegistro2;
        this.imagen = imagen;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getGaceta() {
        return gaceta;
    }

    public void setGaceta(String gaceta) {
        this.gaceta = gaceta;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getIdPublPCT() {
        return idPublPCT;
    }

    public void setIdPublPCT(String idPublPCT) {
        this.idPublPCT = idPublPCT;
    }

    public String getIdSolicitudPCT() {
        return idSolicitudPCT;
    }

    public void setIdSolicitudPCT(String idSolicitudPCT) {
        this.idSolicitudPCT = idSolicitudPCT;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Date getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(Date presentacion) {
        this.presentacion = presentacion;
    }

    public Date getfRegistro() {
        return fRegistro;
    }

    public void setfRegistro(Date fRegistro) {
        this.fRegistro = fRegistro;
    }

    public Date getVigencia() {
        return vigencia;
    }

    public void setVigencia(Date vigencia) {
        this.vigencia = vigencia;
    }

    public String getTipoGaceta() {
        return tipoGaceta;
    }

    public void setTipoGaceta(String tipoGaceta) {
        this.tipoGaceta = tipoGaceta;
    }

    public String getApoderado() {
        return apoderado;
    }

    public void setApoderado(String apoderado) {
        this.apoderado = apoderado;
    }

    public String getDiseno() {
        return diseno;
    }

    public void setDiseno(String diseno) {
        this.diseno = diseno;
    }

    public String getTitularDir() {
        return titularDir;
    }

    public void setTitularDir(String titularDir) {
        this.titularDir = titularDir;
    }

    public String getPresentacion2() {
        return presentacion2;
    }

    public void setPresentacion2(String presentacion2) {
        this.presentacion2 = presentacion2;
    }

    public String getfRegistro2() {
        return fRegistro2;
    }

    public void setfRegistro2(String fRegistro2) {
        this.fRegistro2 = fRegistro2;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    


}
