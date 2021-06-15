package ca.aquiletour.core.models.user;

import ca.aquiletour.core.utils.Validator;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.users.NtroUser;

public class User extends NtroUser {
	
	private String uuid = "";
	private String firstname = "";
	private String lastname = "";
	private String email = "";

	private String passwordHash = "";
	private boolean hasPassword = false;
	private boolean hasName = false;

	public User() {

	}

	public User(String email) {
		this.email = email;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public boolean getHasName() {
		return hasName;
	}

	public void setHasName(boolean hasName) {
		this.hasName = hasName;
	}

	public User toPublicUser() {
		T.call(this);
		
		User publicUser = Ntro.factory().newInstance(this.getClass());
		
		publicUser.copyPublicInfomation(this);
		
		return publicUser;
	}

	public void copyPublicInfomation(User user) {
		T.call(this);

		setId(user.getId());
		setUuid(user.getUuid());
		setFirstname(user.getFirstname());
		setLastname(user.getLastname());
		setEmail(user.getEmail());
		setHasPassword(user.getHasPassword());
		setHasName(user.getHasName());
	}

	public User toSessionUser() {
		User sessionUser = Ntro.factory().newInstance(this.getClass());

		copySessionOnlyInfo(sessionUser);

		return sessionUser;
	}

	protected void copySessionOnlyInfo(User sessionUser) {
		T.call(this);

		sessionUser.setId(getId());
		sessionUser.setUuid(getUuid());
		sessionUser.setAuthToken(getAuthToken());
		sessionUser.setFirstname(getFirstname());
		sessionUser.setLastname(getLastname());
		sessionUser.setEmail(getEmail());
		sessionUser.setHasPassword(getHasPassword());
		sessionUser.setHasName(getHasName());
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

	public boolean hasPassword() {
		T.call(this);
		
		return getHasPassword();
	}

	public static String normalizeUserId(String userId) {
		T.call(User.class);

		return Validator.normalizeId(userId.toLowerCase());
	}


	public String displayName() {
		T.call(User.class);
		
		String displayName = formatDisplayName(getFirstname(), getLastname());
		
		if(displayName.isEmpty()) {
			
			displayName = getId();
		}

		return displayName;
	}


	public static String formatDisplayName(String firstname, String lastname) {
		T.call(User.class);
		
		String displayName = "";
		
		if(firstname != null && !firstname.isEmpty()) {
			displayName = firstname;
		}
		
		if(lastname != null && !lastname.isEmpty()) {
			if(!displayName.isEmpty()) {
				displayName += " ";
			}
			
			displayName += lastname;
		}

		return displayName;
	}


}
