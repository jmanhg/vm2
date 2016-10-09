package mx.com.oposicion.support;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanLocator {
	private static ClassPathXmlApplicationContext appContext = 
			new ClassPathXmlApplicationContext("spring/applicationContext*.xml");
	
	private static BeanFactory beanFactory = (BeanFactory) appContext;
	
	public static Object getBean(String beanName) {
		return beanFactory.getBean(beanName);
	}

}
