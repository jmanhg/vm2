/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.oposicion.exposition;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.servlet.http.HttpSession;



import mx.com.oposicion.model.CatViena;
import mx.com.oposicion.persistence.CatVienaMapper;
import mx.com.oposicion.service.CatVienaService;
import mx.com.oposicion.support.MessagesController;
import mx.com.oposicion.support.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@FacesConverter("catVienaConverter")
public class CatVienaConverter implements Converter {

    
    private HttpSession session= (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    
    static  List<PojoLista> catVienaListDB;
    
    public CatVienaConverter() {
      
        if (session.getAttribute("listaCatVienas") != null) {
            catVienaListDB= (List<PojoLista>) session.getAttribute("listaCatVienas");
        }
    }
     
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue.trim().equals("")) {
            return null;
        }else if (!Util.isNumeric(submittedValue.trim())) {
            return null;
        } else {
            try {
                int number = Integer.parseInt(submittedValue);

                for (PojoLista p : catVienaListDB) {
                    if (p.getNumber() == number) {
                        return p;
                    }
                }

            } catch(NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error: no es un catViena valido", "no es un catViena valido"));
                //msgControler.addError("Conversion Error: no es un catViena valido", "Conversion Error: no es un catViena valido");
            }
        }

    

        return null;
    }
    
   
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((PojoLista) value).getNumber());
        }
    }
}
                    