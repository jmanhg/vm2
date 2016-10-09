/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.oposicion.exposition;

import java.util.Comparator;
import org.primefaces.model.SortOrder;
import mx.com.oposicion.model.OposicionData;
 
public class OposicionLazySorter implements Comparator<OposicionData> {
 
    private String sortField;
     
    private SortOrder sortOrder;
     
    public OposicionLazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    public int compare(OposicionData opData1, OposicionData opData2) {
        try {
            Object value1 = OposicionData.class.getField(this.sortField).get(opData1);
            Object value2 = OposicionData.class.getField(this.sortField).get(opData2);
 
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}