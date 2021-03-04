package ca.aquiletour.core.models.users;

import ca.ntro.core.NtroUser;

public class User extends NtroUser {
	
	private String name;
	private String surname;
	private String userEmail;
	private String userPassword;
	private String phoneNumber;	
	public User() {
		
	}
	
	public User(String email, String password) {
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	
	
}
