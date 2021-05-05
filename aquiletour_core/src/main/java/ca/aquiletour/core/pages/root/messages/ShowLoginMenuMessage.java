package ca.aquiletour.core.pages.root.messages;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.messages.NtroMessage;

public class ShowLoginMenuMessage extends NtroMessage {
	
	private String messageToUser;
	private List<NtroMessage> delayedMessages = new ArrayList<>();

	public String getMessageToUser() {
		return messageToUser;
	}

	public void setMessageToUser(String messageToUser) {
		this.messageToUser = messageToUser;
	}

	public List<NtroMessage> getDelayedMessages() {
		return delayedMessages;
	}

	public void setDelayedMessages(List<NtroMessage> delayedMessages) {
		this.delayedMessages = delayedMessages;
	}
}
