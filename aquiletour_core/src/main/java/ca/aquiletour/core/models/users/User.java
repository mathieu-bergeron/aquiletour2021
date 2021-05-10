package ca.aquiletour.core.models.users;

import ca.ntro.core.system.trace.T;
import ca.ntro.users.NtroUser;

public class User extends NtroUser {
	
	private String firstname = "";
	private String lastname = "";
	private String email = "";

	private String passwordHash = "";
	private boolean hasPassword = false;

	public User() {

	}

	public User(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public boolean isHasPassword() {
		return hasPassword;
	}

	public void setHasPassword(boolean hasPassword) {
		this.hasPassword = hasPassword;
	}

	public void copyPublicInfomation(User user) {
		setFirstname(user.getFirstname());
		setLastname(user.getLastname());
		setEmail(user.getEmail());
	}

	public User toSessionUser() {
		User sessionUser = new User();

		copySessionOnlyInfo(sessionUser);

		return sessionUser;
	}

	protected void copySessionOnlyInfo(User sessionUser) {
		sessionUser.setId(getId());
		sessionUser.setAuthToken(getAuthToken());
		sessionUser.setFirstname(getFirstname());
		sessionUser.setLastname(getLastname());
		sessionUser.setEmail(getEmail());
	}

	public boolean actsAsTeacher() {
		T.call(this);
		
		return false;
	}
	
	
}
