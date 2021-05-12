package ca.aquiletour.core.models.user_registration;

import ca.ntro.core.models.NtroModel;

public class UserIdModel implements NtroModel {

	private String userId = "";

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
