package ca.aquiletour.core.models.users;

public class Student extends User {

	private String programId = "";

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	@Override
	public void copyPublicInformation(User otherUser) {
		super.copyPublicInformation(otherUser);

		setProgramId(((Student)otherUser).getProgramId());
	}
	
}
