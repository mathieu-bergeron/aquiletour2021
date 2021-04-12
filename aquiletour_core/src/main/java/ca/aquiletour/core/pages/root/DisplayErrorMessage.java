package ca.aquiletour.core.pages.root;

import ca.ntro.messages.NtroMessage;

public class DisplayErrorMessage extends NtroMessage {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
