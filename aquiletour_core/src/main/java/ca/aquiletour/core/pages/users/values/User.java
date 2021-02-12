package ca.aquiletour.core.pages.users.values;

import ca.ntro.core.models.properties.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class User extends NtroModelValue {
	
	private String userId;
	private String authToken;
	private String name;
	private String surname;
	private String userEmail;
	private String userPassword;

	public User() {
		
	}
	
	public User(String email, String password) {
		this.userEmail = email;
		this.userPassword = password;;
		
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}

	public boolean isValid(String queryToken) {
		T.call(this);
		
		boolean isValid = false;
		
		if(authToken != null) {
			
			isValid = authToken.equals(queryToken);
		}
		
		return isValid;
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
