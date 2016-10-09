package mx.com.oposicion.exposition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import mx.com.oposicion.support.MessagesController;
import mx.com.oposicion.support.Util;

// librerias para exportacion
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import fonetic.BusquedaFonetica;
import fonetic.Gacetadata;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.com.oposicion.model.OposicionData;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import fonetic.Gacetadata;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import mx.com.oposicion.model.Reporte;
import mx.com.oposicion.model.ReporteDatos;
import mx.com.oposicion.model.ReporteFonetico;
import mx.com.oposicion.service.ReporteDatosService;
import mx.com.oposicion.service.ReporteFoneticoService;
import mx.com.oposicion.service.ReporteService;
import org.primefaces.component.column.Column;
import org.primefaces.context.RequestContext;
import org.primefaces.event.data.SortEvent;

//@Controller
//@Scope("request")
@Controller
@Scope(value = "view")
public class OposicionRep implements Serializable {

    private final static long serialVersionUID = 1L;
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

    @Autowired
    private MessagesController msgControler;
    
    @Autowired
    private DataSourceTransactionManager transactionManager;
    
    @Autowired
    private ReporteService reporteService;
    @Autowired
    private ReporteDatosService reporteDatosService;
    @Autowired
    private ReporteFoneticoService reporteFoneticoService;

    @Value("${menssages.debug}")
    private boolean msgDebug;

    private UploadedFile file;
    private String path;
    private int numRegs;
    private double coeficiente;
    private List<OposicionData> oposicionDat= new ArrayList<OposicionData>();
    private List<OposicionData> oposicionResultados= new ArrayList<OposicionData>();
    private OposicionDataModel oposicionDataModel;
    private OposicionData oposicionDataSelected= new OposicionData();
    private String reporteName;
    private FoneticDataModel foneticDataModel;
    private Gacetadata foneticDataSelected= new Gacetadata(0, "", "","", "", "", "", "", "", 0);
    
    private Reporte reporte=new Reporte();
    private ReporteDatos reporteDatos= new ReporteDatos();
    private ReporteFonetico reporteFonetico= new ReporteFonetico();
    private List<ReporteDatos> reportesDatos= new ArrayList<ReporteDatos>();
    private List<ReporteFonetico> reportesFonetico= new ArrayList<ReporteFonetico>();

    private double progress = 0d;
    private String message = "";
    
    @PostConstruct
    public void init() {
      this.coeficiente =40.00;
    }

    public void handleFileUpload1(FileUploadEvent event) {
        String path = OposicionRep.class.getResource("OposicionRep.class").getPath();
        path = path.substring(0, path.indexOf("/WEB-INF")) + "/content/upload/";
        this.file = event.getFile();
        this.setPath(this.file.getFileName() );
       this.stop=false;
        BufferedReader br;
        try {
            oposicionDat= new ArrayList<OposicionData>();
            oposicionResultados= new ArrayList<OposicionData>();
            br = new BufferedReader(new InputStreamReader(file.getInputstream()));
            String currentLine;
            int numLine=0;
            while ((currentLine = br.readLine()) != null) {
                numLine++;
                System.out.println((numLine)+"-"+currentLine);
                if(currentLine!=null){
                    String[] datos=currentLine.split("\t");
                    if(datos.length==4){
                        if(isInteger(datos[3])){
                            if(isClases(datos[1])){
                                oposicionDat.add(new OposicionData(numLine,datos[0],datos[1],datos[2],Integer.parseInt(datos[3]),null,null,""));
                            }else{
                                System.out.println("No se proceso por clases incorrectas");
                            }
                        }else{
                         System.out.println("No se proceso por numRegs incorrectos");
                        }
                    }else{
                         System.out.println("No se proceso por argumentos incorrectos");
                    }
                }
            }
            
        } catch (IOException ex) {
            msgControler.addInfo("Información", "No se recupero el archivo:"+ex.getMessage() );
        }
        msgControler.addInfo("Aviso", "Archivo " + event.getFile().getFileName() + " cargado, se generará el Fonetico");
        BusquedaFonetica busquedaFonetica=new BusquedaFonetica();
         try {
             
             double progressInc=(double)100d/oposicionDat.size();
             progress=0d;
             int i=1;
             for (OposicionData oposicionData : oposicionDat) {
                progress+=progressInc;
                message = "Analizando reg["+(i++)+"] "+oposicionData.getDenominacion()+"; clase:"+oposicionData.getClases();
                System.out.println(message);
                 List<Gacetadata> fonetico = busquedaFonetica.BuscaFonetico(oposicionData.getDenominacion(), oposicionData.getClases(), oposicionData.getNumResultados(), "", "","", transactionManager.getDataSource().getConnection(),0);
                 List<Gacetadata> fonTmp= new ArrayList<Gacetadata>();
                 for(Gacetadata g :fonetico){
                     if(g.getCoeficiente()>=this.coeficiente){
                         fonTmp.add(g);
                     }
                 }
                 
                    oposicionData.setFonetico(fonTmp);
                    oposicionData.setNumResultados(fonTmp.size());
                    oposicionData.setReporte(generateDocument(this.getClass().getResource("").getPath(),oposicionData,fonTmp));
                    oposicionResultados.add(oposicionData);
                    oposicionDataModel = new OposicionDataModel(oposicionResultados);
                    numRegs=oposicionResultados.size();  
                 
             }
               //oposicionDataModel = new OposicionDataModel(oposicionResultados);
               if(sinCeros) borraFonetic(1);
               Collections.sort(oposicionResultados, new Comparator<OposicionData>(){
                    public int compare(OposicionData s1, OposicionData s2) {
                        // return s1.getNumResultados() - s2.getNumResultados(); //ascending
                        return s2.getNumResultados() - s1.getNumResultados();  // descending
                    }
                });
               oposicionDataModel = new OposicionDataModel(oposicionResultados);
               progress=100d;
               this.stop=true;
               message = "Se generó el fonético de "+oposicionResultados.size()+" registros";
               msgControler.addInfo("Información", message );
        } catch (Exception ex) {
                msgControler.addInfo("Información", "Ocurrio un erro al generar el fonetico:"+ex.getMessage() );
            }
            this.stop=true;

            
        
    }
public static boolean isClases(String s) {
    try { 
        String[] clasesIn=s.split(" ");
        for(String cla:clasesIn){
            Integer.parseInt(cla); 
        }
    } catch(NumberFormatException e) { 
        return false; 
    } catch(NullPointerException e) {
        return false;
    }
    return true;
}

public static boolean isInteger(String s) {
    try { 
        Integer.parseInt(s); 
    } catch(NumberFormatException e) { 
        return false; 
    } catch(NullPointerException e) {
        return false;
    }
    // only got here if we didn't return false
    return true;
}
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
     public void generarReporte(byte[] fileRep) {
         try{
         if (fileRep == null) {
                msgControler.addInfo("Aviso", "No existen reporte ");
                return;
            }
            InputStream stream = null;
            ByteArrayOutputStream outStream = null;
            session.removeAttribute("reporteStream");

            
                ByteArrayOutputStream byt = null;
                List<byte[]> mm = new ArrayList<byte[]>();

                
                byt = Util.concatenarPdfs(fileRep, mm);

                stream = new ByteArrayInputStream(byt.toByteArray());
                outStream = new ByteArrayOutputStream();
                outStream.write(byt.toByteArray());
                session.setAttribute("reporteStream", outStream);
     
        } catch (Exception ex) {
            ex.printStackTrace();
            msgControler.addInfo("No se genero el Reporte: ", ex.getMessage() + "");
        }
    }
 public byte[] generateDocument(String basePath, OposicionData od ,List<fonetic.Gacetadata> gacetadata) throws Exception {

        basePath = basePath.replace("/WEB-INF/classes/mx/com/oposicion/exposition/", "");
        basePath = basePath.replace("/C:", "C:");
        basePath = basePath.replace("/c:", "c:");
        String sourceFileName = basePath.replace("WEB-INF/classes/mx/com/oposicion/exposition/", "") + "/content/reportes/oposicionRep.jasper";

        Map parameters = new HashMap();
        parameters.put("logo", basePath + "/images/op.png");

        parameters.put("titulo", "Reporte Fonético de la Denominacion ["+od.getDenominacion()+"] en las clases ["+od.getClases()+"]");

        JRBeanCollectionDataSource desp = new JRBeanCollectionDataSource(gacetadata);

        ByteArrayOutputStream streamReporteGenerado = new ByteArrayOutputStream();
        try {

            JasperPrint jasperPrint = JasperFillManager.fillReport(sourceFileName, parameters, desp);
            JRExporter jasperExport = new JRPdfExporter();
            jasperExport.setParameter(JRExporterParameter.OUTPUT_STREAM, streamReporteGenerado);
            jasperExport.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            jasperExport.exportReport();

        } catch (JRException e) {
            e.printStackTrace();
            throw new Exception("No se genero el reporte revisar Log");
        }
        System.out.println("Se ha generado el documento PDF ");
        return streamReporteGenerado.toByteArray();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<OposicionData> getOposicionDat() {
        return oposicionDat;
    }

    public void setOposicionDat(List<OposicionData> oposicionDat) {
        this.oposicionDat = oposicionDat;
    }

    public List<OposicionData> getOposicionResultados() {
        return oposicionResultados;
    }

    public void setOposicionResultados(List<OposicionData> oposicionResultados) {
        this.oposicionResultados = oposicionResultados;
    }

    public OposicionDataModel getOposicionDataModel() {
        return oposicionDataModel;
    }

    public void setOposicionDataModel(OposicionDataModel oposicionDataModel) {
        this.oposicionDataModel = oposicionDataModel;
    }

    public OposicionData getOposicionDataSelected() {
        return oposicionDataSelected;
    }

    public void setOposicionDataSelected(OposicionData oposicionDataSelected) {
        this.oposicionDataSelected = oposicionDataSelected;
    }

    public FoneticDataModel getFoneticDataModel() {
        return foneticDataModel;
    }

    public void setFoneticDataModel(FoneticDataModel foneticDataModel) {
        this.foneticDataModel = foneticDataModel;
    }

    public Gacetadata getFoneticDataSelected() {
        return foneticDataSelected;
    }

    public void setFoneticDataSelected(Gacetadata foneticDataSelected) {
        this.foneticDataSelected = foneticDataSelected;
    }
public void setFonetico(List<Gacetadata> fModel){
    foneticDataModel = new FoneticDataModel(fModel);
}


public void borraFonetico(Gacetadata gaceta){
        if(gaceta !=null ) {
                List<Gacetadata> fDataModelTmp=new ArrayList<Gacetadata>();
                for (Gacetadata gacetaTmp : foneticDataModel) {
                    if(gaceta.getIdRegistro()!=gacetaTmp.getIdRegistro()){
                        fDataModelTmp.add(gacetaTmp);
                    }
                }
                foneticDataModel =new FoneticDataModel(fDataModelTmp);
                msgControler.addInfo("Elimina:", "Se elimino el registro:["+gaceta.getIdRegistro()+"] "+gaceta.getTitulo());
                
    	} 
}

public void borraRep(OposicionData op){
        if(op !=null ) {
                List<OposicionData> oDataModelTmp=new ArrayList<OposicionData>();
                for (OposicionData opTmp : oposicionResultados) {
                    if(op.getIdRow()==opTmp.getIdRow()){
                        oposicionResultados.remove(opTmp);
                    }
                }
                
                oposicionDataModel =new OposicionDataModel(oposicionResultados);
                oposicionDataSelected=null;
                msgControler.addInfo("Elimina:", "Se elimino el registro:"+op.getDenominacion());
                
    	} 
}

public void borraRep2(OposicionData op){
        if(op !=null ) {
                List<OposicionData> oDataModelTmp=new ArrayList<OposicionData>();
                for (OposicionData opTmp : oposicionDataModel) {
                    if(op.getIdRow()!=opTmp.getIdRow()){
                        oDataModelTmp.add(opTmp);
                    }
                }
                oposicionResultados=oDataModelTmp;
                oposicionDataModel =new OposicionDataModel(oposicionResultados);
                msgControler.addInfo("Elimina:", "Se elimino el registro:["+op.getIdRow()+"] "+op.getDenominacion());
                
    	} 
}

public void borraFonetic(int numRows){
        
                List<OposicionData> oDataModelTmp=new ArrayList<OposicionData>();
                for (OposicionData opTmp : oposicionDataModel) {
                    if(opTmp.getNumResultados()>=numRows){
                        oDataModelTmp.add(opTmp);
                    }
                }
                numRegs=oposicionResultados.size();
                oposicionResultados=oDataModelTmp;
                oposicionDataModel =new OposicionDataModel(oposicionResultados);
                
}

     public void onRowSelectOposicionData(SelectEvent event) {

        OposicionData od=((OposicionData) event.getObject());
        this.oposicionDataSelected=od;
        foneticDataModel = new FoneticDataModel(od.getFonetico());
         msgControler.addInfo("Selected", od.getDenominacion() + "[" + od.getIdRow() + "]");
         

    }
     public String tipoM(int i){
         String valor="";
         switch(i){
            case 1:valor="NOMINATIVA"; break;
            case 2:valor="DISEÑO"; break;
            case 3:valor="MIXTA"; break;
            case 4:valor="TRIDIMENSIONAL"; break;
            case 5:valor="MIXTA (Den. y Dis. T.)"; break;
            case 6:valor="MIXTA (Dis. y Forma T.)"; break;
            case 7:valor="MIXTA (Den., Dis. y Forma T.)"; break;
             }
         return valor;
     }
     public void onRowSelectGacetadata(SelectEvent event) {

        Gacetadata od=((Gacetadata) event.getObject());
        this.foneticDataSelected=od;

         msgControler.addInfo("Selected", od.getTitulo() + "[" + od.getIdRegistro() + "]");
         

    }
     
     public void guardaDetalle() {
         
         
         
         
                List<Gacetadata> fDataModelTmp=new ArrayList<Gacetadata>();
                for (Gacetadata gacetaTmp : foneticDataModel) {
                        fDataModelTmp.add(gacetaTmp);
                    
                }
                this.oposicionDataSelected.setFonetico(fDataModelTmp);
                this.oposicionDataSelected.setNumResultados(fDataModelTmp.size());
                try {
                    this.oposicionDataSelected.setReporte(generateDocument(this.getClass().getResource("").getPath(),this.oposicionDataSelected,fDataModelTmp));
                } catch (Exception ex) {
                     msgControler.addInfo("Guardar", "No se genero el reporte de :"+this.oposicionDataSelected.getIdRow()+"-"+this.oposicionDataSelected.getDenominacion() + "[" + this.oposicionDataSelected.getClases() + "]");
                }

            
         msgControler.addInfo("Guardar", "Se guardo el reporte de la Denominación:"+this.oposicionDataSelected.getIdRow()+"-"+this.oposicionDataSelected.getDenominacion() + "[" + this.oposicionDataSelected.getClases() + "]");
         
     }
     
     public void guardaDetalle2() {
         
         
         
         List<OposicionData> oResultados= new ArrayList<OposicionData>();
         for (OposicionData oposicionData : oposicionResultados) {
            if(this.oposicionDataSelected.getIdRow()==oposicionData.getIdRow()){
                List<Gacetadata> fDataModelTmp=new ArrayList<Gacetadata>();
                for (Gacetadata gacetaTmp : foneticDataModel) {
                        fDataModelTmp.add(gacetaTmp);
                    
                }
                oposicionData.setFonetico(fDataModelTmp);
                oposicionData.setNumResultados(fDataModelTmp.size());
                try {
                    oposicionData.setReporte(generateDocument(this.getClass().getResource("").getPath(),oposicionData,fDataModelTmp));
                } catch (Exception ex) {
                     msgControler.addInfo("Guardar", "No se genero el reporte de :"+this.oposicionDataSelected.getIdRow()+"-"+this.oposicionDataSelected.getDenominacion() + "[" + this.oposicionDataSelected.getClases() + "]");
                }

            }
            oResultados.add(oposicionData);
        }
             oposicionDataModel = new OposicionDataModel(oResultados);
             this.oposicionResultados=oResultados;
         msgControler.addInfo("Guardar", "Se guardo el reporte de la Denominación:"+this.oposicionDataSelected.getIdRow()+"-"+this.oposicionDataSelected.getDenominacion() + "[" + this.oposicionDataSelected.getClases() + "]");
         
     }
     public void guardaBusqueda() {
         this.reporteName="ReporteEjemplo";
         if(oposicionResultados.size()>0){
         //reporte=new Reporte(oposicionResultados.size(), reporteName, null, null, 0);
         reporteService.inserta(reporte);
         
         for (OposicionData oposicionData : oposicionResultados) {
            
                //reporteDatos=new ReporteDatos(0,reporte.getIdRep() ,oposicionData.getDenominacion() , oposicionData.getClases(), oposicionData.getReporte());
                reporteDatosService.inserta(reporteDatos);
                FoneticDataModel fDataModel = new FoneticDataModel(oposicionData.getFonetico());
                for (Gacetadata gacetaTmp : fDataModel) {
                    //reporteFonetico=new ReporteFonetico(0,reporteDatos.getIdReg(),gacetaTmp.getTipo(),gacetaTmp.getRegistro(),gacetaTmp.getSolicitud(),gacetaTmp.getTitulo(),gacetaTmp.getClase(),gacetaTmp.getGaceta(),gacetaTmp.getCoeficiente());
                    reporteFoneticoService.inserta(reporteFonetico);
                }
                
               
        }
         reporte.setActivo(1);
         reporteService.actualiza(reporte);
         msgControler.addInfo("Guardar", "Se guardo la búsqueda fonética y reportes de "+this.oposicionResultados.size()+" marcas, con nombre: "+this.reporteName);
         }else{
             msgControler.addInfo("Guardar", "No se puede gardar una búsqueda sin resultados");
                
         }
     }
     
     private StreamedContent file1;
public StreamedContent getFile1() {
    exportarBusqueda();
        return file1;
    }
     public void exportarBusqueda() {
         try{
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
int numRow=1;
         for(OposicionData oposicionData : oposicionResultados) {
            zos.putNextEntry(new ZipEntry((numRow++)+"_"+oposicionData.getDenominacion().replace(" ", "_")+"_"+oposicionData.getClases().replace(" ", "_")+".pdf"));
            zos.write(oposicionData.getReporte(), 0, oposicionData.getReporte().length);
            zos.closeEntry();
        }
        zos.flush();
        baos.flush();
        zos.close();
        baos.close();

        
        
        this.file1=new DefaultStreamedContent(new ByteArrayInputStream(baos.toByteArray()), "application/zip", "oposicion"+Calendar.DATE+".zip");
        
         msgControler.addInfo("Exportar", "Se exporto la búsqueda fonética y reportes de "+this.oposicionResultados.size()+" marcas");
         }catch(Exception e){
             msgControler.addInfo("Exportar", "No se exporto la búsqueda fonética y reportes de "+this.oposicionResultados.size()+" marcas ["+e.getMessage()+"]");
         }
     }
    public void reset() {
        //msgControler.addInfo("Limpiar", this.oposicionDataSelected.getDenominacion() + "[" + this.oposicionDataSelected.getIdRow() + "]");
               
    }

    public int getNumRegs() {
        return numRegs;
    }

    public double getCoeficiente() {
        return coeficiente;
    }

    public void setCoeficiente(double coeficiente) {
        this.coeficiente = coeficiente;
    }
    public void setCoeficiente(int coeficiente) {
        this.coeficiente = (double)coeficiente;
    }
public void onComplete() {
        //
        if(this.stop){
            RequestContext reqCtx = RequestContext.getCurrentInstance();
            reqCtx.execute("poll1.stop();");
            //msgControler.addInfo("update", "");
        }
}
    public ReporteService getReporteService() {
        return reporteService;
    }

    public void setReporteService(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    public ReporteDatosService getReporteDatosService() {
        return reporteDatosService;
    }

    public void setReporteDatosService(ReporteDatosService reporteDatosService) {
        this.reporteDatosService = reporteDatosService;
    }

    public ReporteFoneticoService getReporteFoneticoService() {
        return reporteFoneticoService;
    }

    public void setReporteFoneticoService(ReporteFoneticoService reporteFoneticoService) {
        this.reporteFoneticoService = reporteFoneticoService;
    }

    public String getReporteName() {
        return reporteName;
    }

    public void setReporteName(String reporteName) {
        this.reporteName = reporteName;
    }

    public Reporte getReporte() {
        return reporte;
    }

    public void setReporte(Reporte reporte) {
        this.reporte = reporte;
    }

    public ReporteDatos getReporteDatos() {
        return reporteDatos;
    }

    public void setReporteDatos(ReporteDatos reporteDatos) {
        this.reporteDatos = reporteDatos;
    }

    public ReporteFonetico getReporteFonetico() {
        return reporteFonetico;
    }

    public void setReporteFonetico(ReporteFonetico reporteFonetico) {
        this.reporteFonetico = reporteFonetico;
    }

    public List<ReporteDatos> getReportesDatos() {
        return reportesDatos;
    }

    public void setReportesDatos(List<ReporteDatos> reportesDatos) {
        this.reportesDatos = reportesDatos;
    }
/////////////////
    
     
    
   
    public double getProgress()
    {
        return progress;
    }

    public void setProgress(double progress)
    {
        this.progress = progress;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
    
//        private String caseListSortOrder = "DESCENDING";
//
//    public String getCaseListSortOrder() { return caseListSortOrder; }
//    public void setCaseListSortOrder(String caseListSortType) { this.caseListSortOrder = caseListSortType; }
//   
//    private Column caseListSortBy;
//
//    public Column getCaseListSortBy() {return caseListSortBy;}
//    public void setCaseListSortBy(Column caseListSortBy) {this.caseListSortBy = caseListSortBy;}
//   
//    public void caseListSortListner(SortEvent sortEvent){
//        this.caseListSortBy = (Column) sortEvent.getSortColumn();
//        if (sortEvent.isAscending()){
//            this.caseListSortOrder = "ASCENDING";
//        }else{
//             this.caseListSortOrder = "DESCENDING";       
//        }
//    }
    
    private boolean stop = false;

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
    
    private boolean sinCeros=true;

    public boolean isSinCeros() {
        return sinCeros;
    }

    public void setSinCeros(boolean sinCeros) {
        this.sinCeros = sinCeros;
    }
    
}
