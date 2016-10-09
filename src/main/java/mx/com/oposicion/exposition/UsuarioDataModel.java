/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.oposicion.exposition;  
  
import java.util.List;  
import javax.faces.model.ListDataModel;   
import mx.com.oposicion.model.Usuario;
import org.primefaces.model.SelectableDataModel;  
  
public class UsuarioDataModel extends ListDataModel<Usuario> implements SelectableDataModel<Usuario> {    
  
    public UsuarioDataModel() {  
    }  
  
    public UsuarioDataModel(List<Usuario> data) {  
        super(data);  
    }  
      
    @Override  
    public Usuario getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<Usuario> busqueda = (List<Usuario>) getWrappedData();  
          
        for(Usuario b : busqueda) {  
            if(b.getIdUsuario()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(Usuario b) {  
        return b.getIdUsuario();  
    }  
} 
