package mx.com.oposicion.model;

import java.io.Serializable;
import java.util.Date;

public class DateWrapper implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final long oneHour = 60*60*1000;
	private Date upperLimit;

	public DateWrapper(int days) {
		long now = System.currentTimeMillis();
		long when = now -oneHour*24*days;
		upperLimit = new Date(when);
	}
	
	public Date getUpperLimit() {
		return upperLimit;
	}
	public void setUpperLimit(Date upperLimit) {
		this.upperLimit = upperLimit;
	}
	
}
