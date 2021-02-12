package ca.aquiletour.core.pages.users.messages;

import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;

public class DeleteUserMessage extends NtroMessage {

	
	private String userId;

	public String getUserId() {
		T.call(this);

		return userId;
	}

	public void setUserId(String userId) {
		T.call(this);

		this.userId = userId;
	}

	

}
