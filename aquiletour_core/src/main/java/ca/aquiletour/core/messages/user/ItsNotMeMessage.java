package ca.aquiletour.core.messages.user;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.users.User;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.NtroUserMessage;

public class ItsNotMeMessage extends NtroUserMessage<User> {
	
	private List<NtroMessage> delayedMessages = new ArrayList<>();

	public List<NtroMessage> getDelayedMessages() {
		return delayedMessages;
	}

	public void setDelayedMessages(List<NtroMessage> delayedMessages) {
		this.delayedMessages = delayedMessages;
	}
}
