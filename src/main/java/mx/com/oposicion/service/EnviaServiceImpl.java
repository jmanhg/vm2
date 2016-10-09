package mx.com.oposicion.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EnviaServiceImpl implements EnviaService {
	
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	public void doit() {
		try {
			ok();
		} catch (AddressException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void ok() throws AddressException {
		MailEngine me = MailEngine.getInstance("http://localhost:8080/oposicion/index.jsf", 3);
		System.out.println("**********");
		me.setActive(true);
		me.setMailSender(mailSender);
		me.setFrom(new InternetAddress("jmhernandez@impi.gob.mx"));
                ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String logo = servletContext.getRealPath("") + File.separator + "images" + File.separator + "pdf.png";
        
                File[] attachments= new File[1];
                attachments[0]=new File(logo);
                me.addMessage("jmhernandez@impi.gob.mx", "prueba", "<h1>Hola Mundo</h1>",attachments);
		me.sendAllMessages();
	}
	
}
