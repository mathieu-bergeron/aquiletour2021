package ca.ntro.messages.ntro_messages;

import ca.ntro.messages.NtroMessage;
import ca.ntro.users.NtroUser;

public class NtroSetUserMessage extends NtroMessage {
	
	private NtroUser user;

	public NtroUser getUser() {
		return user;
	}

	public void setUser(NtroUser user) {
		this.user = user;
	}
}
