package ca.aquiletour.core.models.user_uuid;

import ca.ntro.core.models.NtroModel;

public class UuidByUserId implements NtroModel {
	
	private String uuid = "";

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
