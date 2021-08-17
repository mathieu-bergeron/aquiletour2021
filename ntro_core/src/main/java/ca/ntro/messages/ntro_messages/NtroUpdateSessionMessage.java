package ca.ntro.messages.ntro_messages;

import ca.ntro.messages.NtroMessage;
import ca.ntro.users.NtroSession;

public class NtroUpdateSessionMessage extends NtroMessage {
	
	private NtroSession session;

	public NtroSession getSession() {
		return session;
	}

	public void setSession(NtroSession session) {
		this.session = session;
	}
}
