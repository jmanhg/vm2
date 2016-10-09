/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.oposicion.support;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mx.com.oposicion.exposition.PojoLista;
import mx.com.oposicion.model.CatViena;

/**
 *
 * @author juan
 */
public class  Util {
    
    private static List<PojoLista> listaCatViena ;
    
    
    
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?"); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static String getYear(Date d) {
        if(d==null) return "";
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy");
        return formateador.format(d);
    }
    //Metodo usado para obtener la fecha actual
    //@return Retorna un <b>STRING</b> con la fecha actual formato "dd-MM-yyyy"
    public static String getFechaActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        return formateador.format(ahora);
    }

    //Metodo usado para obtener la hora actual del sistema
    //@return Retorna un <b>STRING</b> con la hora actual formato "hh:mm:ss"
    public static String getHoraActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("hh:mm:ss");
        return formateador.format(ahora);
    }

    //Sumarle dias a una fecha determinada
    //@param fch La fecha para sumarle los dias
    //@param dias Numero de dias a agregar
    //@return La fecha agregando los dias
    public static java.sql.Date sumarFechasDias(Date fch, int dias) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fch.getTime());
        cal.add(Calendar.DATE, dias);
        return new java.sql.Date(cal.getTimeInMillis());
    }

    //Restarle dias a una fecha determinada
    //@param fch La fecha
    //@param dias Dias a restar
    //@return La fecha restando los dias
    //public static synchronized java.sql.Date restarFechasDias(java.sql.Date fch, int dias) {
    public static synchronized java.sql.Date restarFechasDias(Date fch, int dias) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fch.getTime());
        cal.add(Calendar.DATE, -dias);
        return new java.sql.Date(cal.getTimeInMillis());
    }

    //Diferencias entre dos fechas
    //@param fechaInicial La fecha de inicio
    //@param fechaFinal  La fecha de fin
    //@return Retorna el numero de dias entre dos fechas
    public static synchronized int diferenciasDeFechas(Date fechaInicial, Date fechaFinal) {

        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String fechaInicioString = df.format(fechaInicial);
        try {
            fechaInicial = df.parse(fechaInicioString);
        } catch (ParseException ex) {
        }

        String fechaFinalString = df.format(fechaFinal);
        try {
            fechaFinal = df.parse(fechaFinalString);
        } catch (ParseException ex) {
        }

        long fechaInicialMs = fechaInicial.getTime();
        long fechaFinalMs = fechaFinal.getTime();
        long diferencia = fechaFinalMs - fechaInicialMs;
        double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
        return ((int) dias);
    }

    //Devuele un java.util.Date desde un String en formato dd-MM-yyyy
    //@param La fecha a convertir a formato date
    //@return Retorna la fecha en formato Date
    public static synchronized java.util.Date deStringToDate(String fecha) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaEnviar = null;
        try {
            fechaEnviar = formatoDelTexto.parse(fecha);
            return fechaEnviar;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static synchronized String deStringToString(String fecha,SimpleDateFormat formato1,SimpleDateFormat formato2) {
        Date fechaEnviar = null;
        try {
            fechaEnviar = formato1.parse(fecha);
            return formato2.format(fechaEnviar);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static synchronized String deDateToString(Date fecha) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
        String fechaEnviar = null;
        try {
            fechaEnviar = formatoDelTexto.format(fecha);
            return fechaEnviar;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    
    
    public static void updateFaceContextCatViena(List<CatViena> catVienas){
        listaCatViena = new ArrayList<PojoLista>();
        for (CatViena c : catVienas) {
//            System.out.println("cliente:" + c.getId_cliente() + "-" + c.getNombre());
            listaCatViena.add(new PojoLista(c.getCategoria()+"."+c.getDivision()+"."+c.getSeccion()+" - "+c.getDescripcion(), c.getIdkey()));
        }
        if(FacesContext.getCurrentInstance()!=null)
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listaCatVienas", listaCatViena);

    }
    
    
    
    public static ByteArrayOutputStream concatenarPdfs(byte[] baosReportePdf, List<byte[]> anexosPdf) {
        ByteArrayOutputStream baosReporteFinal = new ByteArrayOutputStream();
        try {
            PdfCopyFields pdfCopyFields = new PdfCopyFields(baosReporteFinal);
            pdfCopyFields.addDocument(new PdfReader(baosReportePdf));
            for (byte[] anexoPdf : anexosPdf) {
                PdfReader pdfReader = new PdfReader(anexoPdf);
                pdfCopyFields.addDocument(pdfReader);
            }
            pdfCopyFields.close();
            return baosReporteFinal;
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
   
       }
