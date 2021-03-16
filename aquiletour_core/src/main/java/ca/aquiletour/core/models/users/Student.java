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
	public void copyPublicInfomation(User otherUser) {
		super.copyPublicInfomation(otherUser);

		if(otherUser instanceof Student) {
			setProgramId(((Student)otherUser).getProgramId());
		}
	}

	@Override
	public User toSessionUser() {
		Student sessionUser = new Student();
		
		copySessionOnlyInfo(sessionUser);
		
		return sessionUser;
	}

	@Override
	protected void copySessionOnlyInfo(User sessionUser) {
		super.copySessionOnlyInfo(sessionUser);
		
		if(sessionUser instanceof Student) {
			((Student)sessionUser).setProgramId(getProgramId());
		}
	}
	
}
