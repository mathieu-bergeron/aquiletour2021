package ca.aquiletour.core.models.users;

import ca.ntro.users.NtroUser;

public class User extends NtroUser {
	
	private String name = "";
	private String surname = "";
	private String userEmail = "";
	private String phoneNumber = "";	

	public User() {
		
	}
	
	public User(String email) {
		this.userEmail = email;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void copyPublicInfomation(User user) {
		setName(user.getName());
		setSurname(user.getSurname());
	}

	public User toSessionUser() {
		User sessionUser = new User();

		copySessionOnlyInfo(sessionUser);

		return sessionUser;
	}

	protected void copySessionOnlyInfo(User sessionUser) {
		sessionUser.setId(getId());
		sessionUser.setAuthToken(getAuthToken());
		sessionUser.setName(getName());
		sessionUser.setSurname(getSurname());
	}
	
	
}
