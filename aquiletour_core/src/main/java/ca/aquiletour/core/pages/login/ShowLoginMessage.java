package ca.aquiletour.core.pages.login;

import ca.ntro.messages.NtroMessage;

public class ShowLoginMessage extends NtroMessage {
	
	private String messageToUser = "";

	public String getMessageToUser() {
		return messageToUser;
	}

	public void setMessageToUser(String messageToUser) {
		this.messageToUser = messageToUser;
	}
}
