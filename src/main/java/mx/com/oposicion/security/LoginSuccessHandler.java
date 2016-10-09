package mx.com.oposicion.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

@Service
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Value("${defaultTargetUrl}")
	private String defaultTargetUrl;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,HttpServletResponse response, Authentication authentication)
	        throws ServletException, IOException {
                System.out.println("exito");
                super.setDefaultTargetUrl(defaultTargetUrl);
                super.onAuthenticationSuccess(request, response, authentication);
	}
}
