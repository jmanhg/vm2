/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.oposicion.exposition;  
  
import java.util.List;  
import javax.faces.model.ListDataModel;   
import mx.com.oposicion.model.GacetadataViena;
import org.primefaces.model.SelectableDataModel;  
  
public class GacetadataVienaDataModel extends ListDataModel<GacetadataViena> implements SelectableDataModel<GacetadataViena> {    
  
    public GacetadataVienaDataModel() {  
    }  
  
    public GacetadataVienaDataModel(List<GacetadataViena> data) {  
        super(data);  
    }  
      
    @Override  
    public GacetadataViena getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<GacetadataViena> busqueda = (List<GacetadataViena>) getWrappedData();  
          
        for(GacetadataViena b : busqueda) {  
            if(b.getIdkey()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(GacetadataViena b) {  
        return b.getIdkey();  
    }  
} 

//
//public class CarDataModel extends ListDataModel<Car> implements SelectableDataModel<Car> {    
//  
//    public CarDataModel() {  
//    }  
//  
//    public CarDataModel(List<Car> data) {  
//        super(data);  
//    }  
//      
//    @Override  
//    public Car getRowData(String rowKey) {  
//        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
//          
//        List<Car> cars = (List<Car>) getWrappedData();  
//          
//        for(Car car : cars) {  
//            if(car.getModel().equals(rowKey))  
//                return car;  
//        }  
//          
//        return null;  
//    }  
//  
//    @Override  
//    public Object getRowKey(Car car) {  
//        return car.getModel();  
//    }  
//}  