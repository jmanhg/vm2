package mx.com.oposicion.support;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;  

import org.springframework.stereotype.Service;
 
@Service
public class MessagesController {
	public void addInfo(String summary, String details) {  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,summary, details));  
    }  
  
    public void addWarn(String summary, String details) {  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,summary, details));  
    }  
  
    public void addError(String summary, String details) {  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,summary, details));  
    }  
  
    public void addFatal(String summary, String details) {  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,summary, details));  
    }  

}
