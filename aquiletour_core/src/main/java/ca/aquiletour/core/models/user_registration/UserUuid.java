package ca.aquiletour.core.models.user_registration;

import ca.ntro.core.models.NtroModel;

public class UserUuid implements NtroModel {
	
	private String uuid = "";

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
