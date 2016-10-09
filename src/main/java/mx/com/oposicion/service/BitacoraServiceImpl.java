package mx.com.oposicion.service;

import java.sql.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import mx.com.oposicion.model.Bitacora;
import mx.com.oposicion.model.DateWrapper;
import mx.com.oposicion.model.Usuario;
import mx.com.oposicion.persistence.BitacoraMapper;

public class BitacoraServiceImpl implements BitacoraService {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(BitacoraServiceImpl.class);
        @Autowired
	private BitacoraMapper bitacoraMapper;
	
	@Value("${numDaysBitacoraFlush}")
	private int numDaysBitacoraFlush;
	
	@Override
	public void clean() {
		logger.info("ejecutandome: BitacoraService:clean");
		DateWrapper dw = new DateWrapper(numDaysBitacoraFlush);
		bitacoraMapper.clean(dw);
	}
	
	@Override
	public void insert(int action, String extraInfo) {
            String username="Anónimo";
            String ip ="0.0.0.0";
            try{
                 HttpSession session= (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                 HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	
            if (session.getAttribute("Usuario") != null) {
                Usuario user= (Usuario) session.getAttribute("Usuario");
                username=""+user.getUsername();
            } 
		ip = request.getHeader( "X-FORWARDED-FOR" );
                if ( ip == null ) {
                   ip = request.getRemoteAddr();
                }
            }catch(Exception ex){
                
            }
		Date eventDate = new Date(System.currentTimeMillis());
		Bitacora bitacora = new Bitacora(0, username, ip, eventDate, action, extraInfo);
		bitacoraMapper.insert(bitacora);
	}
	
}
