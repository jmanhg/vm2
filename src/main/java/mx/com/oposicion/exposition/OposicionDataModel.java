/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.oposicion.exposition;  
  
import java.util.List;  
import javax.faces.model.ListDataModel;   
import mx.com.oposicion.model.OposicionData;
import org.primefaces.model.SelectableDataModel;  
  
public class OposicionDataModel extends ListDataModel<OposicionData> implements SelectableDataModel<OposicionData> {    
  
    public OposicionDataModel() {  
    }  
  
    public OposicionDataModel(List<OposicionData> data) {  
        super(data);  
    }  
      
    @Override  
    public OposicionData getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<OposicionData> busqueda = (List<OposicionData>) getWrappedData();  
          
        for(OposicionData b : busqueda) {  
            if(b.getIdRow()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(OposicionData b) {  
        return b.getIdRow();  
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