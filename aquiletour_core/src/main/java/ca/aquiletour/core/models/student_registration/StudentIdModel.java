package ca.aquiletour.core.models.student_registration;

import ca.ntro.core.models.NtroModel;

public class StudentIdModel implements NtroModel {

	private String userId = "";

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
