package leettalk.event;

import java.util.Date;

public class LoginEvent {

	private String username;
	private Date date;

	public LoginEvent(String username) {
		super();
		this.username = username;
		date = new Date();
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	

}
