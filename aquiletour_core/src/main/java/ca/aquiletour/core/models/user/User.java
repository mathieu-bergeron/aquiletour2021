package ca.aquiletour.core.models.user;

import ca.ntro.core.system.trace.T;
import ca.ntro.users.NtroUser;

public class User extends NtroUser {
	
	private String registrationId = "";
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

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
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

	public boolean getHasPassword() {
		return hasPassword;
	}

	public void setHasPassword(boolean hasPassword) {
		this.hasPassword = hasPassword;
	}

	public void copyPublicInfomation(User user) {
		T.call(this);

		setRegistrationId(user.getRegistrationId());
		setFirstname(user.getFirstname());
		setLastname(user.getLastname());
		setEmail(user.getEmail());
		setHasPassword(user.getHasPassword());
	}

	public User toSessionUser() {
		User sessionUser = new User();

		copySessionOnlyInfo(sessionUser);

		return sessionUser;
	}

	protected void copySessionOnlyInfo(User sessionUser) {
		T.call(this);

		sessionUser.setId(getId());
		sessionUser.setRegistrationId(getRegistrationId());
		sessionUser.setAuthToken(getAuthToken());
		sessionUser.setFirstname(getFirstname());
		sessionUser.setLastname(getLastname());
		sessionUser.setEmail(getEmail());
		sessionUser.setHasPassword(getHasPassword());
	}

	public boolean actsAsTeacher() {
		T.call(this);
		
		return false;
	}

	public boolean actsAsAdmin() {
		T.call(this);
		
		return false;
	}

	public boolean isGuest() {
		T.call(this);
		
		return true;
	}

	public void resetAfterLogout() {
		T.call(this);
	}

	public void updateFirstNameIfEmpty(String firstName) {
		T.call(this);
		
		if(firstName != null
				&& (getFirstname() == null
				|| getFirstname().isEmpty())) {

			setFirstname(firstName);
		}
	}

	public void updateLastNameIfEmpty(String lastName) {
		T.call(this);

		if(lastName != null
				&& (getLastname() == null
				|| getLastname().isEmpty())) {

			setLastname(lastName);
		}
	}


	public void updateEmailIfEmpty(String email) {
		T.call(this);

		if(email != null
				&& (getEmail() == null
				|| getEmail().isEmpty())) {

			setEmail(email);
		}
	}

	public void updateInfoIfEmpty(String firstName, String lastName, String email) {
		T.call(this);
		
		updateFirstNameIfEmpty(firstName);
		updateLastNameIfEmpty(lastName);
		updateEmailIfEmpty(email);
	}

	public void updateInfoIfEmpty(User user) {
		T.call(this);

		updateFirstNameIfEmpty(user.getFirstname());
		updateLastNameIfEmpty(user.getLastname());
		updateEmailIfEmpty(user.getEmail());
	}
}
