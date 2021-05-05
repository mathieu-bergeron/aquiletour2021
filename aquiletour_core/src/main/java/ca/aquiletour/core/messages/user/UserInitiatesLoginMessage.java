package ca.aquiletour.core.messages.user;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.users.User;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.NtroUserMessage;

public class UserInitiatesLoginMessage extends NtroUserMessage<User> {
	
	private String providedId;
	private List<NtroMessage> delayedMessages = new ArrayList<>();

	public String getProvidedId() {
		return providedId;
	}

	public void setProvidedId(String providedId) {
		this.providedId = providedId;
	}

	public List<NtroMessage> getDelayedMessages() {
		return delayedMessages;
	}

	public void setDelayedMessages(List<NtroMessage> delayedMessages) {
		this.delayedMessages = delayedMessages;
	}
}
