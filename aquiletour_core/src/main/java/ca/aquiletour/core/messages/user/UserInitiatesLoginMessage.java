package ca.aquiletour.core.messages.user;


public class UserInitiatesLoginMessage extends DelayedMessagesMessage {
	
	private String registrationId;

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
}
