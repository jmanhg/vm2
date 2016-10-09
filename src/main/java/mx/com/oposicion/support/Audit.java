package mx.com.oposicion.support;

import mx.com.oposicion.support.BeanLocator;
import mx.com.oposicion.service.BitacoraService;
import mx.com.oposicion.service.BitacoraServiceImpl;

public class Audit {
	
	private static BitacoraService bs = 
			(BitacoraService)BeanLocator.getBean("bitacoraService");

	//private static BitacoraService bs = new BitacoraServiceImpl();
	
	public static void write(int action) {
		write(action, "NO Extra - Info");
	}

	public static void write(int action, String extraInfo) {
		bs.clean();
		bs.insert(action, extraInfo);
	}
	
}
