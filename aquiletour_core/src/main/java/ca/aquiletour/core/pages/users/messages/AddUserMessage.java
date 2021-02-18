package ca.aquiletour.core.pages.users.messages;

import ca.aquiletour.core.models.users.User;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;

public class AddUserMessage extends NtroMessage {

	private User user;

	public void setUser(User user) {
		T.call(this);
		
		this.user = user;
	}
	
	public User getUser() {
		T.call(this);
		
		return user;
	}

}
