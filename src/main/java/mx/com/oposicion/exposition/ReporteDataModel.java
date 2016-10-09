/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.oposicion.exposition;  
  
import java.util.List;  
import javax.faces.model.ListDataModel;   
import mx.com.oposicion.model.Reporte;
import org.primefaces.model.SelectableDataModel;  
  
public class ReporteDataModel extends ListDataModel<Reporte> implements SelectableDataModel<Reporte> {    
  
    public ReporteDataModel() {  
    }  
  
    public ReporteDataModel(List<Reporte> data) {  
        super(data);  
    }  
      
    @Override  
    public Reporte getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<Reporte> busqueda = (List<Reporte>) getWrappedData();  
          
        for(Reporte b : busqueda) {  
            if(b.getIdRep()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(Reporte b) {  
        return b.getIdRep();  
    }  
} 
