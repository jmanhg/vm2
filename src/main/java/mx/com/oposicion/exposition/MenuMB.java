/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.oposicion.exposition;
 
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;  
import org.primefaces.component.menuitem.MenuItem;  
import org.primefaces.component.submenu.Submenu;  
import org.primefaces.model.DefaultMenuModel;  
import org.primefaces.model.MenuModel;  
import org.springframework.beans.factory.annotation.Autowired;
  
@ManagedBean
@SessionScoped
public class MenuMB {  
  
    private MenuModel model=new DefaultMenuModel(); 
    
    @Autowired
    private LoginMB loginMB=new LoginMB();
  
    public MenuMB() {  
          
        //First submenu  
        Submenu submenu = new Submenu();  
        Submenu submenu2 = new Submenu();  
        MenuItem item = new MenuItem();  
        
        if (loginMB.getAccessRol("PERFIL_FONETICO,PERFIL_FIGURATIVO")) {

            submenu = new Submenu();
            submenu.setIcon("ui-icon-search");
            submenu.setLabel("Vigilancia");
//          if(loginMB.getAccessRol("PERFIL_FONETICO")){
//            item = new MenuItem();  
//            item.setValue("Reporte");  
//            item.setUrl("/content/fonetico/OposicionRep.jsf");  
//            item.setIcon("ui-icon-document");
//            submenu.getChildren().add(item); 
//          }
//          if(loginMB.getAccessRol("PERFIL_FIGURATIVO")){
//             }

//            item = new MenuItem();
//            item.setValue("Reporte Vigilancia");
//            item.setUrl("/content/figurativo/OposicionRep2.jsf");
//            item.setIcon("ui-icon-document");
//            submenu.getChildren().add(item);
//            model.addSubmenu(submenu);
//            
//            
                item = new MenuItem();
                item.setValue("Reporte Vigilancia");
                item.setUrl("/content/figurativo/OposicionRep2.jsf");
                item.setIcon("ui-icon-document");
                submenu.getChildren().add(item);
                model.addSubmenu(submenu);
//             if(loginMB.getAccessRol("PERFIL_ADMIN,PERFIL_SUPERVISOR")){   
//                 item = new MenuItem();
//            item.setValue("Reporte Vigilancia Test");
//            item.setUrl("/content/figurativo/OposicionRepL.jsf");
//            item.setIcon("ui-icon-document");
//            submenu.getChildren().add(item);
//            model.addSubmenu(submenu);
//           }
        }

        if (loginMB.getAccessRol("PERFIL_CAP_VIENA")) {
            submenu = new Submenu();
            submenu.setIcon("ui-icon-search");
            submenu.setLabel("Viena");
            item = new MenuItem();
            item.setValue("Clasificador");
            item.setUrl("/content/captura/OposicionCaptura.jsf");
            item.setIcon("ui-icon-document");
            submenu.getChildren().add(item);
            model.addSubmenu(submenu);
        }
       
        
        if (loginMB.getAccessRol("PERFIL_GACETA")) {
            submenu = new Submenu();
            submenu.setIcon("ui-icon-search");
            submenu.setLabel("Gaceta");
            item = new MenuItem();
            item.setValue("Consultar Gacetas");
            item.setUrl("/content/gaceta/Gaceta.jsf");
            item.setIcon("ui-icon-document");
            submenu.getChildren().add(item);
            model.addSubmenu(submenu);
        }

       
        
        if(loginMB.getAccessRol("PERFIL_ADMIN,PERFIL_SUPERVISOR")){
        
        
        }
        
        if(loginMB.getAccessRol("PERFIL_ADMIN")){
        //5 submenu  
        submenu = new Submenu();  
        submenu.setIcon("ui-icon-pencil");
        submenu.setLabel("Administración");  
        
        
        submenu2 = new Submenu();  
        submenu2.setIcon("ui-icon-folder-open");
        submenu2.setLabel("Catalogos");  
          
        item = new MenuItem();  
        item.setValue("Usuarios");  
        item.setUrl("/content/admin/users/Usuarios.jsf");  
        item.setIcon("ui-icon-person");
        submenu2.getChildren().add(item);  
        
        item = new MenuItem();  
        item.setValue("Perfiles");  
        item.setUrl("#");  
        item.setIcon("ui-icon-person");
        //submenu2.getChildren().add(item);  
        
        submenu.getChildren().add(submenu2);
        
        model.addSubmenu(submenu);  
        }
        
        //6 submenu  
        submenu = new Submenu();  
        submenu.setIcon("ui-icon-help");
        submenu.setLabel("Ayuda");  
          
        item = new MenuItem();  
        item.setValue("Contenido");  
        item.setUrl("/content/Contenido.jsf");  
        item.setIcon("ui-icon-note");
        submenu.getChildren().add(item);  
          
        item = new MenuItem();  
        item.setValue("Powered by");  
        item.setUrl("/content/Powered.jsf");  
        item.setIcon("ui-icon-suitcase");
        submenu.getChildren().add(item);  
          
        item = new MenuItem();  
        item.setValue("Acerca de");  
        item.setUrl("/content/About.jsf"); 
        //item.setOncomplete("dlgAbout.show();");
        item.setIcon("ui-icon-info");
        submenu.getChildren().add(item);  
        
        model.addSubmenu(submenu);  
        
        //7 submenu  
        submenu = new Submenu();  
        submenu.setIcon("ui-icon-home");
        submenu.setLabel("Sistema");  
          
        item = new MenuItem();  
        item.setValue("Acerca de");  
        item.setUrl("/content/About.jsf"); 
        //item.setOncomplete("dlgAbout.show();");
        item.setIcon("ui-icon-info");
        submenu.getChildren().add(item);  
          
        
//        model.addSubmenu(submenu);  
        
    }  
  
    public MenuModel getMenu() {  
        return model;  
    }
}
