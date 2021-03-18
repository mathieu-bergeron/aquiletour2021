package ca.aquiletour.core.messages;

import ca.aquiletour.core.models.users.User;
import ca.ntro.messages.NtroUserMessage;

public class UserInitiatesLoginMessage extends NtroUserMessage<User> {
	
	private String providedId;

	public String getProvidedId() {
		return providedId;
	}

	public void setProvidedId(String providedId) {
		this.providedId = providedId;
	}
}
