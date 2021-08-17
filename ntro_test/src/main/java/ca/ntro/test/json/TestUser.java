package ca.ntro.test.json;

import ca.ntro.users.NtroUser;

public class TestUser extends NtroUser {
	
	private String name;
	private String surname;
	private String userEmail;
	private String userPassword;

	public TestUser() {
		
	}
	
	public TestUser(String email, String password) {
		this.userEmail = email;
		this.userPassword = password;;
		
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
