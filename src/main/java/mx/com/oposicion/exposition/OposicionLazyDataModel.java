package mx.com.oposicion.exposition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import mx.com.oposicion.exposition.OposicionLazySorter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import mx.com.oposicion.model.OposicionData;
 
/**
 * Dummy implementation of LazyDataModel that uses a list to mimic a real datasource like a database.
 */
public class OposicionLazyDataModel extends LazyDataModel<OposicionData> {
     
    private List<OposicionData> datasource;
     
    public OposicionLazyDataModel(List<OposicionData> datasource) {
        this.datasource = datasource;
    }
     
    @Override
    public OposicionData getRowData(String rowKey) {
        for(OposicionData opData : datasource) {
            if(opData.getIdRow()==Integer.parseInt(rowKey))
                return opData;
        }
 
        return null;
    }
 
    @Override
    public Object getRowKey(OposicionData opData) {
        return opData.getIdRow();
    }
    @Override
    public void setRowIndex(int rowIndex) {
        /*
         * The following is in ancestor (LazyDataModel):
         * this.rowIndex = rowIndex == -1 ? rowIndex : (rowIndex % pageSize);
         */
        if (rowIndex == -1 || getPageSize() == 0) {
            super.setRowIndex(-1);
        }
        else
            super.setRowIndex(rowIndex % getPageSize());
    }
    @Override
    public List<OposicionData> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
        List<OposicionData> data = new ArrayList<OposicionData>();
 
        //filter
        for(OposicionData opData : datasource) {
            boolean match = true;
 
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(opData.getClass().getField(filterProperty).get(opData));
 
                        if(filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                    }
                    else {
                            match = false;
                            break;
                        }
                    } catch(Exception e) {
                        match = false;
                    }
                }
            }
 
            if(match) {
                data.add(opData);
            }
        }
 
        //sort
        if(sortField != null) {
            Collections.sort(data, new OposicionLazySorter(sortField, sortOrder));
        }
 
        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);
 
        //paginate
        if(dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            }
            catch(IndexOutOfBoundsException e) {
//                if(pageSize==0) return data.subList(first, first );
                return data.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return data;
        }
    }


}