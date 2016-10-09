package mx.com.oposicion.model;

import java.util.Date;

public class Bitacora {
	private int id;
	private String username;
	private String ip;
	private Date eventDate;
	private int action;
	private String extraInfo;
	
	public Bitacora() {
	}
	
	public Bitacora(
			int id, 
			String username, 
			String ip, 
			Date eventDate, 
			int action,
			String extraInfo) {
		this.id = id;
		this.username = username;
		this.ip = ip;
		this.eventDate = eventDate;
		this.action = action;
		this.extraInfo = extraInfo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}
	
}

