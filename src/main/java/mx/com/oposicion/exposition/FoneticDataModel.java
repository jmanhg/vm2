/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.oposicion.exposition;  
  
import java.util.List;  
import javax.faces.model.ListDataModel;   
import fonetic.Gacetadata;
import org.primefaces.model.SelectableDataModel;  
  
public class FoneticDataModel extends ListDataModel<Gacetadata> implements SelectableDataModel<Gacetadata> {    
  
    public FoneticDataModel() {  
    }  
  
    public FoneticDataModel(List<Gacetadata> data) {  
        super(data);  
    }  
      
    @Override  
    public Gacetadata getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<Gacetadata> busqueda = (List<Gacetadata>) getWrappedData();  
          
        for(Gacetadata b : busqueda) {  
            if(b.getIdRegistro()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(Gacetadata b) {  
        return b.getIdRegistro();  
    }  
} 
