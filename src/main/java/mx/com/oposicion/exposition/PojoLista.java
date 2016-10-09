/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.oposicion.exposition;

import java.io.Serializable;

/**
 *
 * @author juan
 */

public class PojoLista implements Serializable{

    private String  name;
    private int number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    public PojoLista(String name, int number) {
       this.name=name;
       this.number=number;
       
    }
    
}
