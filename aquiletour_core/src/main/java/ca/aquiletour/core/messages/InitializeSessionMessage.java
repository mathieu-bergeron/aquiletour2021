package ca.aquiletour.core.messages;

import ca.ntro.messages.NtroMessage;
import ca.ntro.users.NtroUser;

public class InitializeSessionMessage extends NtroMessage {
	
	private NtroUser sessionUser;

	public NtroUser getSessionUser() {
		return sessionUser;
	}

	public void setSessionUser(NtroUser sessionUser) {
		this.sessionUser = sessionUser;
	}
}
