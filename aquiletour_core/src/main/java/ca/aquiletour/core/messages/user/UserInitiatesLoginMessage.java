package ca.aquiletour.core.messages.user;


public class UserInitiatesLoginMessage extends DelayedMessagesMessage {
	
	private String providedId;

	public String getProvidedId() {
		return providedId;
	}

	public void setProvidedId(String providedId) {
		this.providedId = providedId;
	}
}
