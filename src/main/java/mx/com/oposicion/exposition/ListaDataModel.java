/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.oposicion.exposition;  
  
import java.util.List;  
import javax.faces.model.ListDataModel;   
import mx.com.oposicion.model.Lista;
import org.primefaces.model.SelectableDataModel;  
  
public class ListaDataModel extends ListDataModel<Lista> implements SelectableDataModel<Lista> {    
  
    public ListaDataModel() {  
    }  
  
    public ListaDataModel(List<Lista> data) {  
        super(data);  
    }  
      
    @Override  
    public Lista getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<Lista> busqueda = (List<Lista>) getWrappedData();  
          
        for(Lista b : busqueda) {  
            if(b.getIdlista()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(Lista b) {  
        return b.getIdlista();  
    }  
} 
