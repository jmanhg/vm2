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
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.faces.event.PhaseId;
import javax.imageio.ImageIO;
import mx.com.oposicion.model.CatViena;
import mx.com.oposicion.model.GacetadataViena;
import mx.com.oposicion.model.Lista;
import mx.com.oposicion.model.ListaDato;
import mx.com.oposicion.model.Reporte;
import mx.com.oposicion.model.ReporteDatos;
import mx.com.oposicion.model.ReporteFonetico;
import mx.com.oposicion.model.Usuario;
import mx.com.oposicion.service.CatVienaService;
import mx.com.oposicion.service.GacetadataService;
import mx.com.oposicion.service.GacetadataVienaService;
import mx.com.oposicion.service.ListaDatoService;
import mx.com.oposicion.service.ListaService;
import mx.com.oposicion.service.ReporteDatosService;
import mx.com.oposicion.service.ReporteFoneticoService;
import mx.com.oposicion.service.ReporteService;
import mx.com.oposicion.support.Util;
import org.primefaces.component.column.Column;
import org.primefaces.context.RequestContext;
import org.primefaces.event.data.SortEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

//@Controller
//@Scope("request")
@Controller
@Scope(value = "view")
public class OposicionCapturaMB implements Serializable {

    private final static long serialVersionUID = 1L;
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

    @Autowired
    private MessagesController msgControler;
    
    @Autowired
    private DataSourceTransactionManager transactionManager;
    
    @Autowired
    private ReporteService reporteService;
    
    @Autowired
    private ListaService listaService;
    
    @Autowired
    private ListaDatoService listaDatoService;
    
    @Autowired
    private ReporteDatosService reporteDatosService;
    
    @Autowired
    private ReporteFoneticoService reporteFoneticoService;
    
    @Autowired
    private GacetadataService gacetadataService;
    
    @Autowired
    private LoginMB loginMB; 
    
    @Autowired
    private GacetadataVienaService gacetadataVienaService;
    
    @Autowired
    private CatVienaService catVienaService;

    @Value("${menssages.debug}")
    private boolean msgDebug;
    
    @Autowired
    private SessionRegistry sessionRegistry;

    private UploadedFile file;
    private String path;
    private int numRegs;
    private int numRegsBusqueda;
    private int idRegistro;
    private String registro;
    private String tipo;
    private String subtipo;
    private String solicitud;
    private String denominacion;
    private String clase;
    private String titular;
    private String apoderado;
    private double coeficiente;
    private int numOcurrencias;
    private Date fechaPub1;
    private Date fechaPub2;
    private PojoLista selectedCatViena;
    private String idCatViena;
    private List<PojoLista> listaCatViena;
    private List<OposicionData> oposicionDat= new ArrayList<OposicionData>();
    private List<Lista> listas= new ArrayList<Lista>();
    private List<mx.com.oposicion.model.Gacetadata> gacetasdata= new ArrayList<mx.com.oposicion.model.Gacetadata>();
    private List<Lista> listaResultados= new ArrayList<Lista>();
    private List<Reporte> reporteResultados= new ArrayList<Reporte>();
    private List<OposicionData> oposicionResultados= new ArrayList<OposicionData>();
    private List<GacetadataViena> gVienaResultados= new ArrayList<GacetadataViena>();
    private ListaDataModel listaDataModel;
    private ReporteDataModel reporteDataModel;
    private GacetadataDataModel gacetadataDataModel;
    private GacetadataDataModel gacetadataDataModelV;
    private OposicionDataModel oposicionDataModel;
    private GacetadataVienaDataModel gacetadataVienaDataModel;
    private GacetadataVienaDataModel gacetadataVienaDataModelV;
    private OposicionData oposicionDataSelected= new OposicionData();
    private GacetadataViena gacetadataVienaSelected= new GacetadataViena();
    private GacetadataViena gacetadataVienaSelectedV= new GacetadataViena();
    private Lista listaSelected=new Lista();
    private Reporte reporteSelected=new Reporte();
    private mx.com.oposicion.model.Gacetadata gacetadataSelected= new mx.com.oposicion.model.Gacetadata();
    private mx.com.oposicion.model.Gacetadata gacetadataSelectedV= new mx.com.oposicion.model.Gacetadata();
    private String reporteName;
    private String listaName;
    private FoneticDataModel foneticDataModel;
    private FigurativoDataModel figurativoDataModel;
    private Gacetadata foneticDataSelected= new Gacetadata(0, "", "","", "", "", "", "", "", 0);
    private mx.com.oposicion.model.Gacetadata figurativoDataSelected= new mx.com.oposicion.model.Gacetadata();
    private int tipoBusqueda;
    private Usuario user =null;
    private ReporteFonetico reporteFonetico= new ReporteFonetico();
    private List<ReporteDatos> reportesDatos= new ArrayList<ReporteDatos>();
    private List<ReporteFonetico> reportesFonetico= new ArrayList<ReporteFonetico>();

    private double progress = 0d;
    private String message = "";
    
    
    
    @PostConstruct
    public void init() {
  
        
    if (session.getAttribute("Usuario") != null) {
      this.user = (Usuario) session.getAttribute("Usuario");
    }
    if(!loginMB.getAccessRol("PERFIL_CAP_VIENA")){
              try {
                  FacesContext.getCurrentInstance().getExternalContext().dispatch("/content/common/denegado.jsf");
              } catch (IOException ex) {
                  Logger.getLogger(OposicionRep2MB.class.getName()).log(Level.SEVERE, null, ex);
              }
}
      this.coeficiente =40.00;
      this.numOcurrencias=1;
     if(session.getAttribute("listaCatVienas") == null){
            Util.updateFaceContextCatViena(catVienaService.getAll());
        }
      
      //listaCatViena = CatVienaConverter.catVienaListDB;

      this.fechaPub1=null;
      this.fechaPub2=null;
    }
public void expireUserSessions(String username) {
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            if (principal instanceof Usuario) {
                Usuario userDetails = (Usuario) principal;
                if (userDetails.getUsername().equals(username)) {
                    for (SessionInformation information : sessionRegistry.getAllSessions(userDetails, true)) {
                        information.expireNow();
                    }
                }
            }
        }
    }
    public String image(byte[] img, String attribute) throws IOException {
        String val = null;
        if (img != null) {
            InputStream stream = null;
            ByteArrayOutputStream outStream = null;
            session.removeAttribute(attribute);
            stream = new ByteArrayInputStream(img);
            outStream = new ByteArrayOutputStream();
            outStream.write(img);
            session.setAttribute(attribute, img);
            val = "img";
        }
        return val;
    }
    public StreamedContent image2(byte[] img) {
        byte[] data = null;
        if (img == null) {
            return null;
        }
        try {
            InputStream in = new ByteArrayInputStream(img);
            BufferedImage bi = ImageIO.read(in);

            ByteArrayOutputStream bas = new ByteArrayOutputStream();

            ImageIO.write(bi, "gif", bas);
            data = bas.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        InputStream is = new ByteArrayInputStream(data);
        return new DefaultStreamedContent(is, "image/gif");
    }
    
    public void searchRegistroF(int op){
        
        mx.com.oposicion.model.Gacetadata g=new mx.com.oposicion.model.Gacetadata();
        if(op==1 || (op==0 && (this.idRegistro>0 ||
                (this.tipo!=null && this.tipo.length()>0) ||
                (this.subtipo!=null && this.subtipo.length()>0) ||
                (this.registro!=null && this.registro.length()>0) ||
                (this.solicitud!=null && this.solicitud.length()>0) ||
                (this.denominacion!=null && this.denominacion.length()>0) ||
                 (this.clase!=null && this.clase.length()>0) ||
                 (this.titular!=null && this.titular.length()>0) ||
                 (this.apoderado!=null && this.apoderado.length()>0)
                ))){
            if(this.idRegistro>0) g.setIdRegistro(this.idRegistro);
            if(this.tipo!=null && this.tipo.length()>0) g.setTipo(this.tipo);
            if(this.subtipo!=null && this.subtipo.length()>0) g.setSubtipo(this.subtipo);
            if(this.registro!=null && this.registro.length()>0) g.setRegistro(this.registro);
            if(this.solicitud!=null && this.solicitud.length()>0) g.setSolicitud(this.solicitud);
            if(this.denominacion!=null && this.denominacion.length()>0) g.setTitulo(this.denominacion);
            if(this.clase!=null && this.clase.length()>0) g.setClase(this.clase);
            if(this.titular!=null && this.titular.length()>0) g.setTitular(this.titular);
            if(this.apoderado!=null && this.apoderado.length()>0) g.setApoderado(this.apoderado);
        //gacetasdata=new ArrayList<mx.com.oposicion.model.Gacetadata>();
        gacetasdata=gacetadataService.encuentraPorV(g);
        if(gacetadataDataModel!=null){
            for (mx.com.oposicion.model.Gacetadata g1 : gacetadataDataModel) {
                gacetasdata.add(g1);
            }
        }
        this.numRegsBusqueda=gacetasdata.size();
        gacetadataDataModel=new GacetadataDataModel(gacetasdata);
        msgControler.addInfo("Información", "se encotraron "+gacetasdata.size()+" registros" );
        }else{
             msgControler.addInfo("Información", "Debe de insertar algun valor" );
        }
    }
    public void searchLimpia(){
        gacetadataDataModel=null;
        this.numRegsBusqueda=0;
        msgControler.addInfo("Información", "Se limpio la tabla" );
    }
    public void searchLimpiaV(){
        gacetadataDataModelV=null;
        msgControler.addInfo("Información", "Se limpio la tabla" );
    }
    public void searchRegistro(){
        
        mx.com.oposicion.model.Gacetadata g=new mx.com.oposicion.model.Gacetadata();
        if(this.idRegistro>0 ||
                (this.tipo!=null && this.tipo.length()>0) ||
                (this.subtipo!=null && this.subtipo.length()>0) ||
                (this.registro!=null && this.registro.length()>0) ||
                (this.solicitud!=null && this.solicitud.length()>0) ||
                (this.denominacion!=null && this.denominacion.length()>0) ||
                 (this.clase!=null && this.clase.length()>0) ||
                 (this.titular!=null && this.titular.length()>0) ||
                 (this.apoderado!=null && this.apoderado.length()>0)
                ){
            if(this.idRegistro>0) g.setIdRegistro(this.idRegistro);
            if(this.tipo!=null && this.tipo.length()>0) g.setTipo(this.tipo);
            if(this.subtipo!=null && this.subtipo.length()>0) g.setSubtipo(this.subtipo);
            if(this.registro!=null && this.registro.length()>0) g.setRegistro(this.registro);
            if(this.solicitud!=null && this.solicitud.length()>0) g.setSolicitud(this.solicitud);
            if(this.denominacion!=null && this.denominacion.length()>0) g.setTitulo(this.denominacion);
            if(this.clase!=null && this.clase.length()>0) g.setClase(this.clase);
            if(this.titular!=null && this.titular.length()>0) g.setTitular(this.titular);
            if(this.apoderado!=null && this.apoderado.length()>0) g.setApoderado(this.apoderado);
        //gacetasdata=new ArrayList<mx.com.oposicion.model.Gacetadata>();
        gacetasdata=gacetadataService.encuentraPor(g);
        if(gacetadataDataModel!=null){
            for (mx.com.oposicion.model.Gacetadata g1 : gacetadataDataModel) {
                gacetasdata.add(g1);
            }
        }
        gacetadataDataModel=new GacetadataDataModel(gacetasdata);
        msgControler.addInfo("Información", "se encotraron "+gacetasdata.size()+" registros" );
        }else{
             msgControler.addInfo("Información", "Debe de insertar algun valor" );
        }
    }
    public void searchRegistroV(){
        
        mx.com.oposicion.model.Gacetadata g=new mx.com.oposicion.model.Gacetadata();
        if(this.idRegistro>0 ||
                (this.tipo!=null && this.tipo.length()>0) ||
                (this.subtipo!=null && this.subtipo.length()>0) ||
                (this.registro!=null && this.registro.length()>0) ||
                (this.solicitud!=null && this.solicitud.length()>0) ||
                (this.denominacion!=null && this.denominacion.length()>0) ||
                 (this.clase!=null && this.clase.length()>0) ||
                 (this.titular!=null && this.titular.length()>0) ||
                 (this.apoderado!=null && this.apoderado.length()>0)
                ){
            if(this.idRegistro>0) g.setIdRegistro(this.idRegistro);
            if(this.tipo!=null && this.tipo.length()>0) g.setTipo(this.tipo);
            if(this.subtipo!=null && this.subtipo.length()>0) g.setSubtipo(this.subtipo);
            if(this.registro!=null && this.registro.length()>0) g.setRegistro(this.registro);
            if(this.solicitud!=null && this.solicitud.length()>0) g.setSolicitud(this.solicitud);
            if(this.denominacion!=null && this.denominacion.length()>0) g.setTitulo(this.denominacion);
            if(this.clase!=null && this.clase.length()>0) g.setClase(this.clase);
            if(this.titular!=null && this.titular.length()>0) g.setTitular(this.titular);
            if(this.apoderado!=null && this.apoderado.length()>0) g.setApoderado(this.apoderado);
        //gacetasdata=new ArrayList<mx.com.oposicion.model.Gacetadata>();
        gacetasdata=gacetadataService.encuentraPorV(g);
        if(gacetadataDataModelV!=null){
            for (mx.com.oposicion.model.Gacetadata g1 : gacetadataDataModelV) {
                gacetasdata.add(g1);
            }
        }
        gacetadataDataModelV=new GacetadataDataModel(gacetasdata);
        msgControler.addInfo("Información", "se encotraron "+gacetasdata.size()+" registros" );
        }else{
             msgControler.addInfo("Información", "Debe de insertar algun valor" );
        }
    }
    public void handleFileUpload1(FileUploadEvent event) {
        this.tipoBusqueda=2;
        String path = OposicionCapturaMB.class.getResource("OposicionRep2MB.class").getPath();
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
//                    oposicionResultados.add(oposicionData);
//                    oposicionDataModel = new OposicionDataModel(oposicionResultados);
                    if(sinCeros){
                        if(oposicionData.getNumResultados()>0){
                            oposicionResultados.add(oposicionData);
                            oposicionDataModel = new OposicionDataModel(oposicionResultados);
                        }
                    }else{
                        oposicionResultados.add(oposicionData);
                        oposicionDataModel = new OposicionDataModel(oposicionResultados);
                    }
                    
                    numRegs=oposicionResultados.size();  
                 
             }
               //oposicionDataModel = new OposicionDataModel(oposicionResultados);
//               if(sinCeros) borraFonetic(1);
               Collections.sort(oposicionResultados, new Comparator<OposicionData>(){
                    public int compare(OposicionData s1, OposicionData s2) {
                        // return s1.getNumResultados() - s2.getNumResultados(); //ascending
                        return s2.getNumResultados() - s1.getNumResultados();  // descending
                    }
                });
               oposicionDataModel = new OposicionDataModel(oposicionResultados);
               progress=100d;
               this.stop=true;
               message = "Se encontraron "+oposicionResultados.size()+" registros";
               msgControler.addInfo("Información", message );
        } catch (Exception ex) {
                msgControler.addInfo("Información", "Ocurrio un erro al generar el fonetico:"+ex.getMessage() );
            }
            this.stop=true;

            
        
    }
    public String replaceComa(String dato){
        return dato.replace(",", ",<br>").replace("<b>", "<b style='background-color: yellow;'>");
    }
    public void searchFonetico() {
        this.tipoBusqueda=2;
        String path = OposicionCapturaMB.class.getResource("OposicionRep2MB.class").getPath();
        path = path.substring(0, path.indexOf("/WEB-INF")) + "/content/upload/";
        this.setPath(path );
        this.stop=false;
        
            oposicionDat= new ArrayList<OposicionData>();
            oposicionResultados= new ArrayList<OposicionData>();
            int numLine=0;
            for (mx.com.oposicion.model.Gacetadata g : gacetadataDataModel) {
                if(g.getTitulo()!=null && g.getTitulo().length()>0 && g.getClase()!=null && g.getClase().length()>0){
                    numLine++;
                    System.out.println((numLine)+"-"+g.getIdRegistro()+"-"+g.getTitulo());
                    oposicionDat.add(new OposicionData(numLine,g.getIdRegistro(),g.getTipo(),g.getSubtipo(),g.getRegistro(),g.getSolicitud(), g.getTitulo(),g.getClase(),g.getTitular(),getApo(g.getApoderado()),100,null,null,null,null,null,null, "", Integer.parseInt(g.getDiseno())));
                }else{
                    System.out.println("No se proceso:idRegistro["+g.getIdRegistro()+"]-["+g.getTitulo()+"]");
                }
            }
            
           
        msgControler.addInfo("Aviso", "Registros cargados: " + numLine + ", se generará el Fonetico");
        BusquedaFonetica busquedaFonetica=new BusquedaFonetica();
         try {
             
             String strWhere="";
             if(this.fechaPub1!=null && this.fechaPub2!=null && this.fechaPub1.before(fechaPub2)){
                strWhere=" fecha_presentacion between '"+parseDate(this.fechaPub1,"yyyy/MM/dd")+"' and '"+parseDate(this.fechaPub2,"yyyy/MM/dd")+"' ";
             }
             
             double progressInc=(double)100d/oposicionDat.size();
             progress=0d;
             int i=1;
             for (OposicionData oposicionData : oposicionDat) {
                progress+=progressInc;
                message = "Analizando reg["+(i++)+"] "+oposicionData.getDenominacion()+"; clase:"+oposicionData.getClases();
                System.out.println(message);
                 List<Gacetadata> fonetico = busquedaFonetica.BuscaFonetico(oposicionData.getDenominacion(), oposicionData.getClases(), oposicionData.getNumResultados(), "", "",strWhere, transactionManager.getDataSource().getConnection(),0);
                 List<Gacetadata> fonTmp= new ArrayList<Gacetadata>();
                 for(Gacetadata g :fonetico){
                     if(g.getCoeficiente()>=this.coeficiente){
                         fonTmp.add(g);
                     }
                 }
                 
                    oposicionData.setFonetico(fonTmp);
                    oposicionData.setNumResultados(fonTmp.size());
                    oposicionData.setReporte(generateDocument(this.getClass().getResource("").getPath(),oposicionData,fonTmp));
//                    oposicionResultados.add(oposicionData);
//                    oposicionDataModel = new OposicionDataModel(oposicionResultados);
                    if(sinCeros){
                        if(oposicionData.getNumResultados()>0){
                            oposicionResultados.add(oposicionData);
                            oposicionDataModel = new OposicionDataModel(oposicionResultados);
                        }
                    }else{
                        oposicionResultados.add(oposicionData);
                        oposicionDataModel = new OposicionDataModel(oposicionResultados);
                    }
                    numRegs=oposicionResultados.size();  
                 
             }
               //oposicionDataModel = new OposicionDataModel(oposicionResultados);
//               if(sinCeros) borraFonetic(1);
               Collections.sort(oposicionResultados, new Comparator<OposicionData>(){
                    public int compare(OposicionData s1, OposicionData s2) {
                        // return s1.getNumResultados() - s2.getNumResultados(); //ascending
                        return s2.getNumResultados() - s1.getNumResultados();  // descending
                    }
                });
               oposicionDataModel = new OposicionDataModel(oposicionResultados);
               progress=100d;
               this.stop=true;
               message = "Se encontraron "+oposicionResultados.size()+" registros";
               msgControler.addInfo("Información", message );
        } catch (Exception ex) {
                msgControler.addInfo("Información", "Ocurrio un erro al generar el fonetico:"+ex.getMessage() );
            }
            this.stop=true;

            
        
    }
    
    private String parseDate(Date date, String format) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.format(date);
        } catch (Exception ex) {
            msgControler.addInfo("Error", "al generar el date:" + ex.getMessage());
            return null;
        }
    }
    
    public void searchFigurativo(int bClase) {
        this.tipoBusqueda=bClase;
        String path = OposicionCapturaMB.class.getResource("OposicionRep2MB.class").getPath();
        path = path.substring(0, path.indexOf("/WEB-INF")) + "/content/upload/";
        this.setPath(path );
        this.stop=false;
        
            oposicionDat= new ArrayList<OposicionData>();
            oposicionResultados= new ArrayList<OposicionData>();
            int numLine=0;
            for (mx.com.oposicion.model.Gacetadata g : gacetadataDataModel) {
                numLine++;
                System.out.println((numLine)+"-"+g.getIdRegistro()+"-"+g.getTitulo());
                oposicionDat.add(new OposicionData(numLine,g.getIdRegistro(),g.getTipo(),g.getSubtipo(),g.getRegistro(),g.getSolicitud(),g.getTitulo(),g.getClase(),g.getTitular(),getApo(g.getApoderado()),100,null,null,null,null,null,null, "", Integer.parseInt(g.getDiseno())));
                            
            }
            
           
        msgControler.addInfo("Aviso", "Registros cargados: " + numLine + ", se generará el Figurativo");
        
         try {
             
             double progressInc=(double)100d/oposicionDat.size();
             progress=0d;
             int i=1;
             for (OposicionData oposicionData : oposicionDat) {
                progress+=progressInc;
                message = "Analizando reg["+(i++)+"] "+oposicionData.getDenominacion()+"; clase:"+oposicionData.getClases();
                System.out.println(message);
                
                 List<mx.com.oposicion.model.Gacetadata> figTmp= new ArrayList<mx.com.oposicion.model.Gacetadata>();
                 if(this.tipoBusqueda==0){
                    List<mx.com.oposicion.model.Gacetadata> figurativo = gacetadataService.findFigurativo(Integer.parseInt(oposicionData.getSolicitud()),this.fechaPub1,this.fechaPub2,20);

                    int f=0;
                    for(mx.com.oposicion.model.Gacetadata g :figurativo){
                        f++;
                        if(g.getPagina()>=this.numOcurrencias){
//                            mx.com.oposicion.model.Gacetadata g1=gacetadataService.encuentraBlob(g.getIdRegistro());
//                            g.setImagen(g1.getImagen());
                            g.setGaceta(vienaStyle(gacetadataVienaService.findCodVienaBySolicitudV(oposicionData.getSolicitud()),gacetadataVienaService.findCodVienaBySolicitud(g.getSolicitud())));
                            figTmp.add(g);
                        }
                    }
                 }else{

                     String[] clases = oposicionData.getClases().split(" ");
                     for (String clase : clases) {

                         if (clase != null && !clase.equals("")) {
                             try{
                             List<mx.com.oposicion.model.Gacetadata> figurativo = gacetadataService.findFigurativoClase(Integer.parseInt(oposicionData.getSolicitud()),Integer.parseInt(clase),this.fechaPub1,this.fechaPub2, 20);
                            System.out.println("Procesando figurativo: "+oposicionData.getSolicitud()+":clase:"+clase);
                             int f = 0;
                             for (mx.com.oposicion.model.Gacetadata g : figurativo) {
                                 f++;
                                 if(g.getPagina()>=this.numOcurrencias){
//                                     mx.com.oposicion.model.Gacetadata g1 = gacetadataService.encuentraBlob(g.getIdRegistro());
//                                     g.setImagen(g1.getImagen());
                                     g.setGaceta(vienaStyle(gacetadataVienaService.findCodVienaBySolicitudV(oposicionData.getSolicitud()),gacetadataVienaService.findCodVienaBySolicitud(g.getSolicitud())));
                                     figTmp.add(g);
                                 }
                             }
                         }catch(Exception e){
                                 System.out.println("No se proceso: "+oposicionData.getSolicitud()+":clase:"+clase+"\nError:"+e.getMessage());
                         }
                         }
                     }
                 }
                 
                    oposicionData.setFigurativo(figTmp);
                    oposicionData.setNumResultados(figTmp.size());
                    // oposicionData.setReporteFigurativo(generateDocumentF(this.getClass().getResource("").getPath(),oposicionData,figTmp,this.tipoBusqueda));
                    if(sinCeros){
                        if(oposicionData.getNumResultados()>0){
                            oposicionResultados.add(oposicionData);
                            oposicionDataModel = new OposicionDataModel(oposicionResultados);
                        }
                    }else{
                        oposicionResultados.add(oposicionData);
                        oposicionDataModel = new OposicionDataModel(oposicionResultados);
                    }
                    numRegs=oposicionResultados.size();  
                 
             }
//               //oposicionDataModel = new OposicionDataModel(oposicionResultados);
//               //if(sinCeros) borraFonetic(1);
//               Collections.sort(oposicionResultados, new Comparator<OposicionData>(){
//                    public int compare(OposicionData s1, OposicionData s2) {
//                        // return s1.getNumResultados() - s2.getNumResultados(); //ascending
//                        return s2.getNumResultados() - s1.getNumResultados();  // descending
//                    }
//                });
               oposicionDataModel = new OposicionDataModel(oposicionResultados);
               progress=100d;
               this.stop=true;
               message = "Se encontraron "+oposicionResultados.size()+" registros";
               msgControler.addInfo("Información", message );
        } catch (Exception ex) {
                msgControler.addInfo("Información", "Ocurrio un erro al generar el figurativo:"+ex.getMessage() );
            }
            this.stop=true;

            
        
    }
    
    public String getCodigoVienaV(String solicitud){
        return gacetadataVienaService.findCodVienaBySolicitudV(solicitud);
    }
    
    public String vienaStyle(String searchViena,String codViena){
        String ret=codViena+";";
        String[] sv=searchViena.split(",");
        for(String v:sv){
            ret = ret.replaceAll( v.trim()+",", "<b>"+v.trim()+"</b>, ");
            ret = ret.replaceAll( v.trim()+";", "<b>"+v.trim()+"</b>;");
        }
        return ret.replaceAll(";","");
    }
    
    public String getApo(String apod1) {
        String apod = "";
        if (apod1 != null) {
            String[] ap = apod1.split(":");
            apod = ap[0];
            ap = apod.split(";");
            apod = ap[0];
        }
        return apod;
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
    public void generarReporteFon(OposicionData oposicionDataFon) {
         try{
             byte[] fileRep=null;
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
    public void generarReporteFig(OposicionData oposicionDataFig) {
         try{
             byte[] fileRep=null;
             if(oposicionDataFig.getReporteFigurativo()==null){
              
              List<mx.com.oposicion.model.Gacetadata> figTmp= new ArrayList<mx.com.oposicion.model.Gacetadata>();
                 
              for (mx.com.oposicion.model.Gacetadata g : oposicionDataFig.getFigurativo()) {
                        mx.com.oposicion.model.Gacetadata g1 = gacetadataService.encuentraBlob(g.getIdRegistro());
                        g.setImagen(g1.getImagen());
                        figTmp.add(g);

                }
              fileRep=generateDocumentF(this.getClass().getResource("").getPath(),oposicionDataFig,figTmp,this.tipoBusqueda);
              
              List<OposicionData> oDataModelTmp=new ArrayList<OposicionData>();
                for (OposicionData opTmp : oposicionDataModel) {
                    if(oposicionDataFig.getIdRegistro()==opTmp.getIdRegistro()){
                        this.oposicionDataSelected.setReporteFigurativo(fileRep);
                        opTmp.setReporteFigurativo(fileRep);
                    }
                    oDataModelTmp.add(opTmp);
                }
                oposicionResultados=oDataModelTmp;
                oposicionDataModel =new OposicionDataModel(oposicionResultados);
                
                    
         if (fileRep == null) {
                msgControler.addInfo("Aviso", "No existen reporte ");
                return;
            }
             }else{
                 fileRep=oposicionDataFig.getReporteFigurativo();
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
    public void generarReporteFig2(OposicionData oposicionDataFig) {
         try{
             byte[] fileRep=null;
             if(oposicionDataFig.getReporteFigurativo2()==null){
              
              List<mx.com.oposicion.model.Gacetadata> figTmp= new ArrayList<mx.com.oposicion.model.Gacetadata>();
                 
              for (mx.com.oposicion.model.Gacetadata g : oposicionDataFig.getFigurativo()) {
                        mx.com.oposicion.model.Gacetadata g1 = gacetadataService.encuentraBlob(g.getIdRegistro());
                        g.setImagen(g1.getImagen());
                        figTmp.add(g);

                }
              fileRep=generateDocumentF2(this.getClass().getResource("").getPath(),oposicionDataFig,figTmp,this.tipoBusqueda);
              
              List<OposicionData> oDataModelTmp=new ArrayList<OposicionData>();
                for (OposicionData opTmp : oposicionDataModel) {
                    if(oposicionDataFig.getIdRegistro()==opTmp.getIdRegistro()){
                        this.oposicionDataSelected.setReporteFigurativo2(fileRep);
                        opTmp.setReporteFigurativo2(fileRep);
                    }
                    oDataModelTmp.add(opTmp);
                }
                oposicionResultados=oDataModelTmp;
                oposicionDataModel =new OposicionDataModel(oposicionResultados);
                
                    
         if (fileRep == null) {
                msgControler.addInfo("Aviso", "No existen reporte ");
                return;
            }
             }else{
                 fileRep=oposicionDataFig.getReporteFigurativo2();
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

        parameters.put("titulo", "Reporte Fonético de la solicitud ["+od.getSolicitud()+"], con denominación ["+od.getDenominacion()+"] en las clases ["+od.getClases()+"]");

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

 public byte[] generateDocumentF(String basePath, OposicionData od ,List<mx.com.oposicion.model.Gacetadata> gacetadata,int clase) throws Exception {

        basePath = basePath.replace("/WEB-INF/classes/mx/com/oposicion/exposition/", "");
        basePath = basePath.replace("/C:", "C:");
        basePath = basePath.replace("/c:", "c:");
        String sourceFileName = basePath.replace("WEB-INF/classes/mx/com/oposicion/exposition/", "") + "/content/reportes/oposicionRepF.jasper";

        Map parameters = new HashMap();
        mx.com.oposicion.model.Gacetadata g1=gacetadataService.encuentraBlobV(od.getIdRegistro());
        parameters.put("marca",g1.getImagen());
        parameters.put("logo", basePath + "/images/op.png");

        String claseStr=(clase==0)?" sin clase, ":" con clases ["+od.getClases()+"], ";
        parameters.put("titulo", "Reporte Figurativo de la solicitud ["+od.getSolicitud()+"], con denominación ["+od.getDenominacion()+"]"+claseStr+"con códigos de Viena ["+gacetadataVienaService.findCodVienaBySolicitudV(od.getSolicitud())+"]");

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
 
 public byte[] generateDocumentF2(String basePath, OposicionData od ,List<mx.com.oposicion.model.Gacetadata> gacetadata,int clase) throws Exception {

        basePath = basePath.replace("/WEB-INF/classes/mx/com/oposicion/exposition/", "");
        basePath = basePath.replace("/C:", "C:");
        basePath = basePath.replace("/c:", "c:");
        String sourceFileName = basePath.replace("WEB-INF/classes/mx/com/oposicion/exposition/", "") + "/content/reportes/oposicionRepF2.jasper";

        Map parameters = new HashMap();
        mx.com.oposicion.model.Gacetadata g1=gacetadataService.encuentraBlobV(od.getIdRegistro());
        parameters.put("marca",g1.getImagen());
        parameters.put("logo", basePath + "/images/op.png");

        String claseStr=(clase==0)?" sin clase, ":" con clases ["+od.getClases()+"], ";
        parameters.put("titulo", "Reporte 2 Figurativo de la solicitud ["+od.getSolicitud()+"], con denominación ["+od.getDenominacion()+"]"+claseStr+"con códigos de Viena ["+gacetadataVienaService.findCodVienaBySolicitudV(od.getSolicitud())+"]");

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

    public List<mx.com.oposicion.model.Gacetadata> getGacetasdata() {
        return gacetasdata;
    }

    public void setGacetasdata(List<mx.com.oposicion.model.Gacetadata> gacetasdata) {
        this.gacetasdata = gacetasdata;
    }

    public List<Lista> getListas() {
        return listas;
    }

    public void setListas(List<Lista> listas) {
        this.listas = listas;
    }

   

    public List<OposicionData> getOposicionResultados() {
        return oposicionResultados;
    }

    public void setOposicionResultados(List<OposicionData> oposicionResultados) {
        this.oposicionResultados = oposicionResultados;
    }

    public List<Lista> getListaResultados() {
        return listaResultados;
    }

    public void setListaResultados(List<Lista> listaResultados) {
        this.listaResultados = listaResultados;
    }

    public List<Reporte> getReporteResultados() {
        return reporteResultados;
    }

    public void setReporteResultados(List<Reporte> reporteResultados) {
        this.reporteResultados = reporteResultados;
    }

    public OposicionDataModel getOposicionDataModel() {
        return oposicionDataModel;
    }

    public void setOposicionDataModel(OposicionDataModel oposicionDataModel) {
        this.oposicionDataModel = oposicionDataModel;
    }

    public GacetadataVienaDataModel getGacetadataVienaDataModel() {
        return gacetadataVienaDataModel;
    }

    public void setGacetadataVienaDataModel(GacetadataVienaDataModel gacetadataVienaDataModel) {
        this.gacetadataVienaDataModel = gacetadataVienaDataModel;
    }

    public GacetadataVienaDataModel getGacetadataVienaDataModelV() {
        return gacetadataVienaDataModelV;
    }

    public void setGacetadataVienaDataModelV(GacetadataVienaDataModel gacetadataVienaDataModelV) {
        this.gacetadataVienaDataModelV = gacetadataVienaDataModelV;
    }

    public GacetadataDataModel getGacetadataDataModel() {
        return gacetadataDataModel;
    }

    public void setGacetadataDataModel(GacetadataDataModel gacetadataDataModel) {
        this.gacetadataDataModel = gacetadataDataModel;
    }

    public GacetadataDataModel getGacetadataDataModelV() {
        return gacetadataDataModelV;
    }

    public void setGacetadataDataModelV(GacetadataDataModel gacetadataDataModelV) {
        this.gacetadataDataModelV = gacetadataDataModelV;
    }

    public ListaDataModel getListaDataModel() {
        return listaDataModel;
    }

    public void setListaDataModel(ListaDataModel listaDataModel) {
        this.listaDataModel = listaDataModel;
    }

    public ReporteDataModel getReporteDataModel() {
        return reporteDataModel;
    }

    public void setReporteDataModel(ReporteDataModel reporteDataModel) {
        this.reporteDataModel = reporteDataModel;
    }

    public OposicionData getOposicionDataSelected() {
        return oposicionDataSelected;
    }

    public void setOposicionDataSelected(OposicionData oposicionDataSelected) {
        this.oposicionDataSelected = oposicionDataSelected;
    }

    public GacetadataViena getGacetadataVienaSelected() {
        return gacetadataVienaSelected;
    }

    public void setGacetadataVienaSelected(GacetadataViena gacetadataVienaSelected) {
        this.gacetadataVienaSelected = gacetadataVienaSelected;
    }

    public GacetadataViena getGacetadataVienaSelectedV() {
        return gacetadataVienaSelectedV;
    }

    public void setGacetadataVienaSelectedV(GacetadataViena gacetadataVienaSelectedV) {
        this.gacetadataVienaSelectedV = gacetadataVienaSelectedV;
    }

    public Lista getListaSelected() {
        return listaSelected;
    }

    public void setListaSelected(Lista listaSelected) {
        this.listaSelected = listaSelected;
    }

    public Reporte getReporteSelected() {
        return reporteSelected;
    }

    public void setReporteSelected(Reporte reporteSelected) {
        this.reporteSelected = reporteSelected;
    }

    public mx.com.oposicion.model.Gacetadata getGacetadataSelected() {
        return gacetadataSelected;
    }

    public void setGacetadataSelected(mx.com.oposicion.model.Gacetadata gacetadataSelected) {
        this.gacetadataSelected = gacetadataSelected;
    }

    public mx.com.oposicion.model.Gacetadata getGacetadataSelectedV() {
        return gacetadataSelectedV;
    }

    public void setGacetadataSelectedV(mx.com.oposicion.model.Gacetadata gacetadataSelectedV) {
        this.gacetadataSelectedV = gacetadataSelectedV;
    }

    public FoneticDataModel getFoneticDataModel() {
        return foneticDataModel;
    }

    public void setFoneticDataModel(FoneticDataModel foneticDataModel) {
        this.foneticDataModel = foneticDataModel;
    }

    public int getTipoBusqueda() {
        return tipoBusqueda;
    }

    public void setTipoBusqueda(int tipoBusqueda) {
        this.tipoBusqueda = tipoBusqueda;
    }

    public FigurativoDataModel getFigurativoDataModel() {
        return figurativoDataModel;
    }

    public void setFigurativoDataModel(FigurativoDataModel figurativoDataModel) {
        this.figurativoDataModel = figurativoDataModel;
    }

    public Gacetadata getFoneticDataSelected() {
        return foneticDataSelected;
    }

    public void setFoneticDataSelected(Gacetadata foneticDataSelected) {
        this.foneticDataSelected = foneticDataSelected;
    }

    public mx.com.oposicion.model.Gacetadata getFigurativoDataSelected() {
        return figurativoDataSelected;
    }

    public void setFigurativoDataSelected(mx.com.oposicion.model.Gacetadata figurativoDataSelected) {
        this.figurativoDataSelected = figurativoDataSelected;
    }
    
    
public void setFonetico(List<Gacetadata> fModel){
    foneticDataModel = new FoneticDataModel(fModel);
}

public void setFigurativo(List<mx.com.oposicion.model.Gacetadata> fModel){
    figurativoDataModel = new FigurativoDataModel(fModel);
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

public void borraFigurativo(mx.com.oposicion.model.Gacetadata gaceta){
        if(gaceta !=null ) {
                List<mx.com.oposicion.model.Gacetadata> fDataModelTmp=new ArrayList<mx.com.oposicion.model.Gacetadata>();
                for (mx.com.oposicion.model.Gacetadata gacetaTmp : figurativoDataModel) {
                    if(gaceta.getIdRegistro()!=gacetaTmp.getIdRegistro()){
                        fDataModelTmp.add(gacetaTmp);
                    }
                }
                figurativoDataModel =new FigurativoDataModel(fDataModelTmp);
                msgControler.addInfo("Elimina:", "Se elimino el registro:["+gaceta.getIdRegistro()+"] "+gaceta.getTitulo());
                
    	} 
}

public void borraRep(OposicionData op){
        if(op !=null ) {
//                List<OposicionData> oDataModelTmp=new ArrayList<OposicionData>();
                for (OposicionData opTmp : oposicionResultados) {
                    if(op.getIdRow()==opTmp.getIdRow()){
                        oposicionResultados.remove(opTmp);
                    }
                }
                numRegs=oposicionResultados.size(); 
                message = "Se encontraron "+oposicionResultados.size()+" registros";
                oposicionDataModel =new OposicionDataModel(oposicionResultados);
                oposicionDataSelected=null;
                msgControler.addInfo("Elimina:", "Se elimino el registro:"+op.getDenominacion());
                
    	} 
}

public void borraRepl(Lista op){
    
        if(op !=null ) {
            
                for (Lista opTmp : listaDataModel) {
                    if(op.getIdlista()==opTmp.getIdlista()){
                        listaResultados.remove(opTmp);
                        listaService.borra(opTmp);
                    }
                }
                
                listaDataModel=new ListaDataModel(listaResultados);
                listaSelected=null;
                msgControler.addInfo("Elimina:", "Se elimino el registro:"+op.getNombre());
                
    	} 
}

public void borraRepr(Reporte op){
    
        if(op !=null ) {
            
                for (Reporte opTmp : reporteDataModel) {
                    if(op.getIdRep()==opTmp.getIdRep()){
                        reporteResultados.remove(opTmp);
                        reporteService.borra(opTmp);
                    }
                }
                
                reporteDataModel=new ReporteDataModel(reporteResultados);
                reporteSelected=null;
                msgControler.addInfo("Elimina:", "Se elimino el registro:"+op.getNombre());
                
    	} 
}
public void borraRepg(mx.com.oposicion.model.Gacetadata op){
        if(op !=null ) {
                for (mx.com.oposicion.model.Gacetadata opTmp : gacetasdata) {
                    if(op.getIdRegistro()==opTmp.getIdRegistro()){
                        gacetasdata.remove(opTmp);
                    }
                }
                gacetadataDataModel =new GacetadataDataModel(gacetasdata);
                gacetadataSelected=null;
                msgControler.addInfo("Elimina:", "Se elimino el registro:"+op.getTitulo());
                
    	} 
}

public void borraGacViena(GacetadataViena op){
        if(op !=null ) {
            
            gacetadataVienaService.delete(new GacetadataViena(op.getSolicitud(), op.getIdkey(), op.getCategoria(), op.getDivision(), op.getSeccion(), op.getSecuencia()));
                this.gacetadataVienaDataModel=new GacetadataVienaDataModel(gacetadataVienaService.encuentraSolicitud(op.getSolicitud()));
                 
//                gacetadataVienaDataModel =new GacetadataVienaDataModel(oDataModelTmp);
                gacetadataVienaSelected=null;
                msgControler.addInfo("Elimina:", "Se elimino el registro:"+op.getSolicitud()+" - "+op.getCategoria()+"."+op.getDivision()+"."+op.getSeccion() + "[" + op.getIdkey()+ "]");
                
    	} 
}
public void borraGacVienaV(GacetadataViena op){
        if(op !=null ) {
            
            gacetadataVienaService.deleteV(new GacetadataViena(op.getSolicitud(), op.getIdkey(), op.getCategoria(), op.getDivision(), op.getSeccion(), op.getSecuencia()));
                this.gacetadataVienaDataModelV=new GacetadataVienaDataModel(gacetadataVienaService.encuentraSolicitudV(op.getSolicitud()));
                 
//                gacetadataVienaDataModel =new GacetadataVienaDataModel(oDataModelTmp);
                gacetadataVienaSelectedV=null;
                msgControler.addInfo("Elimina:", "Se elimino el registro:"+op.getSolicitud()+" - "+op.getCategoria()+"."+op.getDivision()+"."+op.getSeccion() + "[" + op.getIdkey()+ "]");
                
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
                numRegs=oposicionResultados.size(); 
                message = "Se encontraron "+oposicionResultados.size()+" registros";
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
                message = "Se encontraron "+oposicionResultados.size()+" registros";
                oposicionResultados=oDataModelTmp;
                oposicionDataModel =new OposicionDataModel(oposicionResultados);
                
}
public String descripcionV(int idkey){
    String ret="";
    ret=catVienaService.encuentra(idkey).getDescripcion();
    return ret;
    
}
     public void onRowSelectOposicionData(SelectEvent event) {

        OposicionData od=((OposicionData) event.getObject());
        this.oposicionDataSelected=od;
        foneticDataModel = new FoneticDataModel(od.getFonetico());
         msgControler.addInfo("Selected", od.getDenominacion() + "[" + od.getIdRow() + "]");
         

    }
     
     public void onRowSelectReporteData(SelectEvent event) {

        Reporte od=((Reporte) event.getObject());
        this.reporteSelected=od;
        this.tipoBusqueda=od.getTipo();
        oposicionResultados= new ArrayList<OposicionData>();
        List<ReporteDatos> reportes=reporteDatosService.encuentra(od.getIdRep());
        
        int numLine=0;
            for (ReporteDatos r : reportes) {
                numLine++;
                System.out.println((numLine)+"-"+r.getIdRegistro()+"-"+r.getDenominacion());
                List<Gacetadata> fon=new ArrayList<Gacetadata>();
                List<mx.com.oposicion.model.Gacetadata> fig=new ArrayList<mx.com.oposicion.model.Gacetadata>();
                if(od.getTipo()==1){
                   List<ReporteFonetico> repf=reporteFoneticoService.encuentra(r.getIdReg());
                   for(ReporteFonetico rf : repf){
                   fig.add(new mx.com.oposicion.model.Gacetadata(rf.getIdRegistro(), rf.getTipo(), rf.getSubtipo(), rf.getRegistro(),
                           rf.getSolicitud(), rf.getDenominacion(), rf.getTitular(), rf.getGaceta(), 0,
                           rf.getClase(), "", "", "","" , null, null, null,
                           "", "", "", "", "", "", null));
                   }
                }else{
                    
                   List<ReporteFonetico> repf=reporteFoneticoService.encuentra(r.getIdReg());
                   for(ReporteFonetico rf : repf){
                   fon.add(new Gacetadata(rf.getIdRegistro(), rf.getTipo(), rf.getSubtipo(), 
                           rf.getRegistro(), rf.getSolicitud(), rf.getDenominacion(), rf.getClase(),
                           rf.getTitular(), rf.getGaceta(), rf.getCoeficiente()));
                   }
                }
                
                oposicionResultados.add(new OposicionData(r.getIdReg(),r.getIdRegistro(),r.getTipo(),
                        r.getSubtipo(),r.getRegistro(),r.getSolicitud(),r.getDenominacion(),r.getClase(),
                        r.getTitular(),getApo(r.getApoderado()),r.getResultados(),r.getFonetico(),r.getEjecutivo(),r.getFigurativo(),r.getFigurativo2(),fon,fig, "", 1));
            }
        
        oposicionDataModel = new OposicionDataModel(oposicionResultados);
        msgControler.addInfo("Información", "se encotraron "+oposicionResultados.size()+" registros" );
        
        
         //msgControler.addInfo("Selected", od.getIdlista()+ "[" + od.getNombre()+ "]");
         

    }
     
     public void onRowSelectListaData(SelectEvent event) {

        Lista od=((Lista) event.getObject());
        this.listaSelected=od;
        gacetasdata=new ArrayList<mx.com.oposicion.model.Gacetadata>();
        List<ListaDato> listaDato=listaDatoService.encuentra(od.getIdlista());
        
        if(listaDato!=null){
            for (ListaDato ld : listaDato) {
                mx.com.oposicion.model.Gacetadata g=gacetadataService.encuentraV(ld.getIdregistro());
                if(g!=null){
                gacetasdata.add(g);
                }
            }
        }
        this.numRegsBusqueda=gacetasdata.size();
        gacetadataDataModel=new GacetadataDataModel(gacetasdata);
        msgControler.addInfo("Información", "se encotraron "+gacetasdata.size()+" registros" );
        
        
         //msgControler.addInfo("Selected", od.getIdlista()+ "[" + od.getNombre()+ "]");
         

    }
     
     public void onRowSelectGacetadataData(SelectEvent event) {

        mx.com.oposicion.model.Gacetadata od=((mx.com.oposicion.model.Gacetadata) event.getObject());
        this.gacetadataSelected=od;
         msgControler.addInfo("Selected", od.getTitulo() + "[" + od.getIdRegistro() + "]");
         

    }
     
     public void onRowSelectGacetadataViena(SelectEvent event) {

        GacetadataViena od=((GacetadataViena) event.getObject());
        this.gacetadataVienaSelected=od;
//        foneticDataModel = new FoneticDataModel(od.getFonetico());
         msgControler.addInfo("Selected", od.getSolicitud()+" - "+od.getCategoria()+"."+od.getDivision()+"."+od.getSeccion() + "[" + od.getIdkey()+ "]");
         

    }
     public void onRowSelectGacetadataVienaV(SelectEvent event) {

        GacetadataViena od=((GacetadataViena) event.getObject());
        this.gacetadataVienaSelectedV=od;
//        foneticDataModel = new FoneticDataModel(od.getFonetico());
         msgControler.addInfo("SelectedV", od.getSolicitud()+" - "+od.getCategoria()+"."+od.getDivision()+"."+od.getSeccion() + "[" + od.getIdkey()+ "]");
         

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
     
      public void onRowSelectGacetadataF(SelectEvent event) {

        mx.com.oposicion.model.Gacetadata od=((mx.com.oposicion.model.Gacetadata) event.getObject());
        this.figurativoDataSelected=od;

         msgControler.addInfo("Selected", od.getTitulo() + "[" + od.getIdRegistro() + "]");
         

    }
     
     public void onRowSelectGacetadataC(SelectEvent event) {

        mx.com.oposicion.model.Gacetadata od=((mx.com.oposicion.model.Gacetadata) event.getObject());
        this.gacetadataSelected=od;
//        gVienaResultados=gacetadataVienaService.encuentraSolicitud(od.getSolicitud());
        this.gacetadataVienaDataModel=new GacetadataVienaDataModel(gacetadataVienaService.encuentraSolicitud(od.getSolicitud()));

         msgControler.addInfo("Selected", od.getTitulo() + "[" + od.getIdRegistro() + "]");
         

    }
     
      public void onRowSelectGacetadataCV(SelectEvent event) {

        mx.com.oposicion.model.Gacetadata od=((mx.com.oposicion.model.Gacetadata) event.getObject());
        this.gacetadataSelectedV=od;
//        gVienaResultados=gacetadataVienaService.encuentraSolicitud(od.getSolicitud());
        this.gacetadataVienaDataModelV=new GacetadataVienaDataModel(gacetadataVienaService.encuentraSolicitudV(od.getSolicitud()));

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
     
     public void guardaDetalleF() {
         
         
         
         
                List<mx.com.oposicion.model.Gacetadata> fDataModelTmp=new ArrayList<mx.com.oposicion.model.Gacetadata>();
                for (mx.com.oposicion.model.Gacetadata gacetaTmp : figurativoDataModel) {
                        fDataModelTmp.add(gacetaTmp);
                    
                }
                this.oposicionDataSelected.setFigurativo(fDataModelTmp);
                this.oposicionDataSelected.setNumResultados(fDataModelTmp.size());
                try {
                    this.oposicionDataSelected.setReporteFigurativo(generateDocumentF(this.getClass().getResource("").getPath(),this.oposicionDataSelected,fDataModelTmp,this.tipoBusqueda));
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
         //Reporte reporte=new Reporte(oposicionResultados.size(), reporteName, null, null, 0);
         //reporteService.inserta(reporte);
         
         for (OposicionData oposicionData : oposicionResultados) {
            
                //ReporteDatos reporteDatos=new ReporteDatos(0,reporte.getIdRep() ,oposicionData.getDenominacion() , oposicionData.getClases(), oposicionData.getReporte());
                //reporteDatosService.inserta(reporteDatos);
                FoneticDataModel fDataModel = new FoneticDataModel(oposicionData.getFonetico());
                for (Gacetadata gacetaTmp : fDataModel) {
                    //reporteFonetico=new ReporteFonetico(0,reporteDatos.getIdReg(),gacetaTmp.getTipo(),gacetaTmp.getRegistro(),gacetaTmp.getSolicitud(),gacetaTmp.getTitulo(),gacetaTmp.getClase(),gacetaTmp.getGaceta(),gacetaTmp.getCoeficiente());
                    reporteFoneticoService.inserta(reporteFonetico);
                }
                
               
        }
//         reporte.setActivo(1);
//         reporteService.actualiza(reporte);
         msgControler.addInfo("Guardar", "Se guardo la búsqueda fonética y reportes de "+this.oposicionResultados.size()+" marcas, con nombre: "+this.reporteName);
         }else{
             msgControler.addInfo("Guardar", "No se puede gardar una búsqueda sin resultados");
                
         }
     }
     public void guardaReporte() {
         
          if (this.user != null) {
         if(oposicionResultados.size()>0 && this.reporteName!=null && this.reporteName.length()>0){
             
         Reporte reporte=new Reporte(0, this.reporteName, null,this.tipoBusqueda,oposicionResultados.size() ,0,this.user.getIdUsuario());
         reporteService.inserta(reporte);
         int i=0;
         for (OposicionData oposicionData : oposicionResultados) {
             
            ReporteDatos reporteDatos=
                    new ReporteDatos(0,reporte.getIdRep() ,oposicionData.getIdRegistro(),oposicionData.getTipo(),oposicionData.getSubtipo(),
                            oposicionData.getRegistro(),oposicionData.getSolicitud(),oposicionData.getDenominacion(),oposicionData.getClases(),
                            oposicionData.getTitular(),oposicionData.getApoderado(),oposicionData.getNumResultados(),null,oposicionData.getReporte(),oposicionData.getReporteEjecutivo(),oposicionData.getReporteFigurativo(),oposicionData.getReporteFigurativo2());
                           
                reporteDatosService.inserta(reporteDatos);
                if (this.tipoBusqueda == 1) {
                    FigurativoDataModel fiDataModel = new FigurativoDataModel(oposicionData.getFigurativo());
                    for (mx.com.oposicion.model.Gacetadata gacetaTmp : fiDataModel) {
                        ReporteFonetico rf = new ReporteFonetico(0, reporteDatos.getIdReg(), gacetaTmp.getIdRegistro(),
                                gacetaTmp.getTipo(), gacetaTmp.getSubtipo(), gacetaTmp.getRegistro(), gacetaTmp.getSolicitud(),
                                gacetaTmp.getTitulo(), "", gacetaTmp.getTitular(), gacetaTmp.getApoderado(), gacetaTmp.getGaceta(),
                                gacetaTmp.getClase(), 0.0, gacetaTmp.getPagina());
                     reporteFoneticoService.inserta(rf);
                 }
                    
                } else {
                    FoneticDataModel fDataModel = new FoneticDataModel(oposicionData.getFonetico());
                    for (Gacetadata gacetaTmp : fDataModel) {
                        ReporteFonetico rf = new ReporteFonetico(0, reporteDatos.getIdReg(), gacetaTmp.getIdRegistro(),
                                gacetaTmp.getTipo(), gacetaTmp.getSubtipo(), gacetaTmp.getRegistro(), gacetaTmp.getSolicitud(),
                                gacetaTmp.getTitulo(), gacetaTmp.getClase(), gacetaTmp.getTitular(), "", gacetaTmp.getGaceta(),
                                "", gacetaTmp.getCoeficiente(), 0);
                     reporteFoneticoService.inserta(rf);
                 }
                }
                
                i++;
         }
         reporte.setActivo(1);
         reporteService.actualiza(reporte);
         reporteResultados=reporteService.getAll();
         reporteDataModel=new ReporteDataModel(reporteResultados);
         msgControler.addInfo("Guardar", "Se guardo la reporte con  "+i+" registros, con nombre: "+this.reporteName);
         }else{
             msgControler.addInfo("Guardar", "No se puede gardar la reporte, falta nombre o no hay registros cargados");
                
         }
         }else{
             msgControler.addInfo("Guardar", "No se puede gardar la reporte, no se ha encontrado el usuario");
                
         }
     }
     public void cargaReporte() {
         
         reporteResultados=reporteService.encuentraByIdUsuario(this.user!=null?this.user.getIdUsuario():-1);
         reporteDataModel=new ReporteDataModel(reporteResultados);
         msgControler.addInfo("Cargar", "Se cargaron la lista con  "+reporteResultados.size()+" registros");
         
     }
      public void cargaLista() {
         
         listaResultados=listaService.encuentraByIdUsuario(this.user!=null?this.user.getIdUsuario():-1);
         listaDataModel=new ListaDataModel(listaResultados);
         msgControler.addInfo("Cargar", "Se cargaron la lista con  "+listaResultados.size()+" registros");
         
     }
     public void guardaLista() {
         
          if (this.user != null) {
            
         if(gacetasdata.size()>0 && this.listaName!=null && this.listaName.length()>0){
         Lista lista=new Lista(0, this.listaName, null, 0,this.user.getIdUsuario());
         listaService.inserta(lista);
         int i=0;
         for (mx.com.oposicion.model.Gacetadata gac : gacetadataDataModel) {
             ListaDato listaDato= new ListaDato(lista.getIdlista(),gac.getIdRegistro());
             listaDatoService.inserta(listaDato);
             i++;
         }
         lista.setActivo(1);
         listaService.actualiza(lista);
         listaResultados=listaService.getAll();
         listaDataModel=new ListaDataModel(listaResultados);
         msgControler.addInfo("Guardar", "Se guardo la lista con  "+i+" registros, con nombre: "+this.listaName);
         }else{
             msgControler.addInfo("Guardar", "No se puede gardar la lista, falta nombre o no hay registros cargados");
                
         }
         }else{
             msgControler.addInfo("Guardar", "No se puede gardar la lista, no se ha encontrado el usuario");
                
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
        int numRow=0;
        List<OposicionData> oDataModelTmp=new ArrayList<OposicionData>();
        
         for(OposicionData oposicionData : oposicionResultados) {
            numRow++;
            List<mx.com.oposicion.model.Gacetadata> figTmp = new ArrayList<mx.com.oposicion.model.Gacetadata>();
            String tipoR="";
            if(this.tipoBusqueda==2){
                tipoR="FON_";
            }else{
                tipoR="FIG_";
            }
            zos.putNextEntry(new ZipEntry((numRow)+"_"+tipoR+"_"+oposicionData.getSolicitud()+"_"+(oposicionData.getDenominacion()==null?"":oposicionData.getDenominacion()).replace(" ", "_")+"_"+oposicionData.getClases().replace(" ", "_")+".pdf"));
            if(this.tipoBusqueda==2){
               zos.write(oposicionData.getReporte(), 0, oposicionData.getReporte().length);
            }else{
                
                if (oposicionData.getReporteFigurativo() != null) {
                    zos.write(oposicionData.getReporteFigurativo(), 0, oposicionData.getReporteFigurativo().length);
                } else {

                    
                    for (mx.com.oposicion.model.Gacetadata g : oposicionData.getFigurativo()) {
                        mx.com.oposicion.model.Gacetadata g1 = gacetadataService.encuentraBlob(g.getIdRegistro());
                        g.setImagen(g1.getImagen());
                        figTmp.add(g);
                    }
                    byte[] fileRep = generateDocumentF(this.getClass().getResource("").getPath(), oposicionData, figTmp, this.tipoBusqueda);
                    oposicionData.setReporteFigurativo(fileRep);
                    zos.write(oposicionData.getReporteFigurativo(), 0, oposicionData.getReporteFigurativo().length);

                }
                //oDataModelTmp.add(oposicionData);
            }
            zos.closeEntry();
            // Reporte 2
            if(!(this.tipoBusqueda==2)){
                tipoR="FIG2_";
                zos.putNextEntry(new ZipEntry((numRow)+"_"+tipoR+"_"+oposicionData.getSolicitud()+"_"+(oposicionData.getDenominacion()==null?"":oposicionData.getDenominacion()).replace(" ", "_")+"_"+oposicionData.getClases().replace(" ", "_")+".pdf"));
              
                if (oposicionData.getReporteFigurativo2() != null) {
                    zos.write(oposicionData.getReporteFigurativo2(), 0, oposicionData.getReporteFigurativo2().length);
                } else {

                    
                    byte[] fileRep = generateDocumentF2(this.getClass().getResource("").getPath(), oposicionData, figTmp, this.tipoBusqueda);
                    oposicionData.setReporteFigurativo2(fileRep);
                    zos.write(oposicionData.getReporteFigurativo2(), 0, oposicionData.getReporteFigurativo2().length);

                }
                oDataModelTmp.add(oposicionData);
                zos.closeEntry();
            }
            
        }
         if(!(this.tipoBusqueda==2)){
            oposicionResultados=oDataModelTmp;
            oposicionDataModel =new OposicionDataModel(oposicionResultados);
         }
        zos.flush();
        baos.flush();
        zos.close();
        baos.close();

        
        
        this.file1=new DefaultStreamedContent(new ByteArrayInputStream(baos.toByteArray()), "application/zip", "vigilanciaMarcaria_"+Util.getFechaActual()+".zip");
        
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

    public int getIdRegistro() {
        return idRegistro;
    }

    public int getNumRegsBusqueda() {
        return numRegsBusqueda;
    }

    public void setNumRegsBusqueda(int numRegsBusqueda) {
        this.numRegsBusqueda = numRegsBusqueda;
    }

    public int getNumOcurrencias() {
        return numOcurrencias;
    }

    public void setNumOcurrencias(int numOcurrencias) {
        this.numOcurrencias = numOcurrencias;
    }

    public Date getFechaPub1() {
        return fechaPub1;
    }

    public void setFechaPub1(Date fechaPub1) {
        this.fechaPub1 = fechaPub1;
    }

    public Date getFechaPub2() {
        return fechaPub2;
    }

    public void setFechaPub2(Date fechaPub2) {
        this.fechaPub2 = fechaPub2;
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

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
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

    public PojoLista getSelectedCatViena() {
        return selectedCatViena;
    }

    public void setSelectedCatViena(PojoLista selectedCatViena) {
        this.selectedCatViena = selectedCatViena;
    }

     public void selectListenerCatViena(SelectEvent event){
//        selectedSolicitante1=null;
//         id_solicitante=0;
        PojoLista itemSelected=(PojoLista) event.getObject();
        this.selectedCatViena=itemSelected;
        this.idCatViena=itemSelected.getNumber()+"";
//        listaSolicitantes=Util.pojoSolicitantes(solicitanteService.findByIdCliente(id_cliente));
        
         msgControler.addInfo("CatViena:", this.idCatViena);
//        search();
    }
     
     public void guardaCV(){
         if(this.selectedCatViena!=null){
             if(this.gacetadataSelected!=null){
               CatViena catViena=  catVienaService.encuentra(this.selectedCatViena.getNumber());
               if(catViena!=null){
                   gacetadataVienaService.insert(new GacetadataViena(this.gacetadataSelected.getSolicitud(), this.selectedCatViena.getNumber(), catViena.getCategoria(), catViena.getDivision(), catViena.getSeccion(), gacetadataVienaService.selectMaxSecuencia(this.gacetadataSelected.getSolicitud())+1));
                this.gacetadataVienaDataModel=new GacetadataVienaDataModel(gacetadataVienaService.encuentraSolicitud(this.gacetadataSelected.getSolicitud()));
                msgControler.addInfo("CatViena:", this.selectedCatViena.getNumber()+":"+catViena.getCategoria()+" : "+catViena.getDivision()+" : "+catViena.getSeccion()+" : "+this.gacetadataSelected.getSolicitud()+" : "+(gacetadataVienaService.selectMaxSecuencia(this.gacetadataSelected.getSolicitud())+1));
               }
             }
         }else{
             msgControler.addInfo("CatViena:", "Debe seleccionar una solicitud");
         }
    }
     
        public void unSelectListenerCatViena(UnselectEvent event){
        PojoLista itemSelected=(PojoLista)  event.getObject();
        idCatViena=itemSelected.getNumber()+"";
        if(msgDebug) msgControler.addInfo("idCatViena:", idCatViena+"");
    }
        
        public List<PojoLista> completeCatViena(String query) {
                listaCatViena = CatVienaConverter.catVienaListDB;
		List<PojoLista> suggestions = new ArrayList<PojoLista>();
		
		for(PojoLista p : listaCatViena) {
			if(p.getName().toUpperCase().startsWith(query.toUpperCase()) || p.getName().toUpperCase().indexOf(query.toUpperCase())>0)
				suggestions.add(p);
		}
		
		return suggestions;
	}

    public String getIdCatViena() {
        return idCatViena;
    }

    public void setIdCatViena(String idCatViena) {
        this.idCatViena = idCatViena;
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

    public ListaService getListaService() {
        return listaService;
    }

    public void setListaService(ListaService listaService) {
        this.listaService = listaService;
    }

    public ListaDatoService getListaDatoService() {
        return listaDatoService;
    }

    public void setListaDatoService(ListaDatoService listaDatoService) {
        this.listaDatoService = listaDatoService;
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

    public GacetadataService getGacetadataService() {
        return gacetadataService;
    }

    public void setGacetadataService(GacetadataService gacetadataService) {
        this.gacetadataService = gacetadataService;
    }

    public CatVienaService getCatVienaService() {
        return catVienaService;
    }

    public void setCatVienaService(CatVienaService catVienaService) {
        this.catVienaService = catVienaService;
    }

    public String getReporteName() {
        return reporteName;
    }

    public void setReporteName(String reporteName) {
        this.reporteName = reporteName;
    }

    public String getListaName() {
        return listaName;
    }

    public void setListaName(String listaName) {
        this.listaName = listaName;
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
