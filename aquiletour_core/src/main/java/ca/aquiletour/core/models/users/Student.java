package ca.aquiletour.core.models.users;

public class Student extends User {
	
	private String registrationId;

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
}
