package mx.com.oposicion.service;

import org.springframework.stereotype.Service;

import mx.com.oposicion.support.GeneralSupport;

@Service
public class CustomPasswordEncoder extends org.springframework.security.authentication.encoding.Md5PasswordEncoder {
	
	@Override
	public String encodePassword(String pass, Object salt) {
		String newPass = GeneralSupport.getPivote() + pass;
                System.out.println("NEWPASS:"+newPass+":SALT:"+salt+":ENCODE:"+super.encodePassword(newPass, salt));
		return super.encodePassword(newPass, salt);
	}
        
}
