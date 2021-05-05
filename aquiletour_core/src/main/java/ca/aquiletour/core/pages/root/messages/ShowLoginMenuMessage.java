package ca.aquiletour.core.pages.root.messages;

import ca.ntro.messages.NtroMessage;

public class ShowLoginMenuMessage extends NtroMessage {
	
	private String messageToUser;

	public String getMessageToUser() {
		return messageToUser;
	}

	public void setMessageToUser(String messageToUser) {
		this.messageToUser = messageToUser;
	}
}
