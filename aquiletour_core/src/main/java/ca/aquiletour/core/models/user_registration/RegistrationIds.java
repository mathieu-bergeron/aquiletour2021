package ca.aquiletour.core.models.user_registration;

import ca.ntro.core.models.NtroModel;

public class RegistrationIds implements NtroModel {
	
	private String registrationId = "";

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
}
