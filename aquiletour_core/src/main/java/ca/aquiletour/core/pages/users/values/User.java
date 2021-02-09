package ca.aquiletour.core.pages.users.values;

import ca.ntro.core.models.properties.NtroModelValue;

public class User extends NtroModelValue {
	
	private String userId;
	private String userEmail;
	private String userPassword;
	
	public User(String email, String password) {
		this.userEmail = email;
		this.userPassword = password;;
		
	}
	public User() {
		
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
}
