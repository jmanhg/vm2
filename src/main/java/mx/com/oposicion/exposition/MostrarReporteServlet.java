/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.com.oposicion.exposition;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mx.com.oposicion.service.GacetadataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


public class MostrarReporteServlet extends HttpServlet {

    Logger log = Logger.getLogger(MostrarReporteServlet.class);
    public final String CONTENT_TYPE = "application/pdf";
    
    @Autowired
    private GacetadataService gacetadataService;

    public GacetadataService getGacetadataService() {
        return gacetadataService;
    }

    public void setGacetadataService(GacetadataService gacetadataService) {
        this.gacetadataService = gacetadataService;
    }
@Override
   public void init() throws ServletException {

SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext (this);
}
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType(request.getParameter("content")!=null?request.getParameter("content"):CONTENT_TYPE);
        //System.out.println("CONTENT_TYPE: "+response.getContentType());
        String ext=request.getParameter("content")!=null?request.getParameter("content"):"pdf";
        ext=ext.replace("application/", "").replace("image/", "");
        response.setHeader("Content-disposition", "inline;archivo."+ext);
        
        //File pdfFile = new File(request.getRealPath("") + "/imagen.jpg");
        //File pdfFile = new File(request.getRealPath("") + "/impi_00_006.jasper");
        //FileInputStream fis = new FileInputStream(pdfFile);
        //String pathFile = request.getRealPath("") + "/impi_00_006.jasper";
        String idRegistro=request.getParameter("idRegistro")!=null?request.getParameter("idRegistro"):null;
        String tipoB=request.getParameter("g")!=null?request.getParameter("g"):null;
        if(idRegistro!=null)
        {
            mx.com.oposicion.model.Gacetadata g;
            if(tipoB!=null){
                g=gacetadataService.encuentraBlobV(Integer.parseInt(idRegistro));
            }
                else{
                g=gacetadataService.encuentraBlob(Integer.parseInt(idRegistro));
            }
        if(g!=null && g.getImagen()!=null){
            response.setContentLength(g.getImagen().length);
            response.getOutputStream().write(g.getImagen());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }else{
            File pdfFile = new File(request.getRealPath("") + "/images/blank.gif");
            FileInputStream fis = new FileInputStream(pdfFile);
            byte[] buf = new byte[(int) pdfFile.length()];
            ByteArrayOutputStream bos= new ByteArrayOutputStream();
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
            bos.write(buf, 0, readNum);
            }
            response.setContentLength(buf.length);
            response.getOutputStream().write(buf);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
        }else{
        HttpSession session = request.getSession();
            ByteArrayOutputStream baos = (ByteArrayOutputStream) session.getAttribute(request.getParameter("attribute")!=null?request.getParameter("attribute"):"reporteStream");
            /*
            byte[] buf = new byte[(int) pdfFile.length()];
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
            bos.write(buf, 0, readNum);
            }
            System.out.println("Parametro: "+request.getParameter("id"));
            response.setContentLength(buf.length);
            response.getOutputStream().write(buf);
             * */
            if (baos != null) {
                response.setContentLength(baos.size());
                response.getOutputStream().write(baos.toByteArray());
                response.getOutputStream().flush();
                response.getOutputStream().close();
                //session.invalidate();
                //session.removeAttribute("reporteStream");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
