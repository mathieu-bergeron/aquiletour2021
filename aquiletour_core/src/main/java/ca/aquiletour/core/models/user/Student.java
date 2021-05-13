package ca.aquiletour.core.models.user;

import ca.ntro.core.system.trace.T;

public class Student extends User {

	private String programId = "";
	private String phoneNumber = "";	

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	@Override
	public boolean isGuest() {
		T.call(this);
		
		return false;
	}


	public void updateProgramIdIfEmpty(String programId) {
		T.call(this);

		if(programId != null
				&& (getProgramId() == null
				|| getProgramId().isEmpty())) {

			setProgramId(programId);
		}
	}

	public void updatePhoneNumberIfEmpty(String phoneNumber) {
		T.call(this);

		if(phoneNumber != null
				&& (getPhoneNumber() == null
				|| getPhoneNumber().isEmpty())) {

			setPhoneNumber(phoneNumber);
		}
		
	}

	public void updateInfoIfEmpty(User user) {
		T.call(this);
		super.updateInfoIfEmpty(user);
		
		if(user instanceof Student) {
			Student student = (Student) user;
			updatePhoneNumberIfEmpty(student.getPhoneNumber());
			updateProgramIdIfEmpty(student.getProgramId());
		}
	}
	
}
