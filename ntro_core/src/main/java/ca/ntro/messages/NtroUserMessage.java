package ca.ntro.messages;

import ca.ntro.core.NtroUser;

public class NtroUserMessage<U extends NtroUser> extends NtroMessage {
	
	private U user;

	public U getUser() {
		return user;
	}

	public void setUser(U user) {
		this.user = user;
	}
}
