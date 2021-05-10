package ca.aquiletour.core.messages;

import ca.aquiletour.core.models.users.User;
import ca.ntro.messages.NtroMessage;

public class InitializeSessionMessage extends NtroMessage {
	
	private User sessionUser;

	public User getSessionUser() {
		return sessionUser;
	}

	public void setSessionUser(User sessionUser) {
		this.sessionUser = sessionUser;
	}
}
