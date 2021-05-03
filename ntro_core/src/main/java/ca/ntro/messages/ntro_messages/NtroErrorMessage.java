package ca.ntro.messages.ntro_messages;

import ca.ntro.messages.NtroMessage;

public class NtroErrorMessage extends NtroMessage {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
