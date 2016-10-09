package mx.com.oposicion.support;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class LoginErrorPhaseListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent arg0) {
//		System.out.println("phaselistener... after");
		
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
//		System.out.println("phaselistener");
            
		String error ="";
                if(FacesContext.getCurrentInstance()!=null)
                error= (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ERROR");
		if( error != null && !error.trim().equals("") ){
			FacesContext.getCurrentInstance().addMessage( null, new FacesMessage( error ) );
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("ERROR");
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

}
