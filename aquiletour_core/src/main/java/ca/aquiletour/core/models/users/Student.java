package ca.aquiletour.core.models.users;

public class Student extends User {
	
	private String registrationId = "";
	private String programId = "";

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	
}
