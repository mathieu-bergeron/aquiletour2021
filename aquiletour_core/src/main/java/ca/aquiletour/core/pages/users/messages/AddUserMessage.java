package ca.aquiletour.core.pages.users.messages;

import ca.aquiletour.core.models.users.User;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;

public class AddUserMessage extends NtroMessage {

	private User User;
	
	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}



}
