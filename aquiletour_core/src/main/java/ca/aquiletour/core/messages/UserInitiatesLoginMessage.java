package ca.aquiletour.core.messages;

import ca.ntro.messages.NtroMessage;

public class UserInitiatesLoginMessage extends NtroMessage {
	
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
