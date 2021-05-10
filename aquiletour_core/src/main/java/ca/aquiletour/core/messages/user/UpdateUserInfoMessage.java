package ca.aquiletour.core.messages.user;

import ca.aquiletour.core.models.users.User;
import ca.ntro.messages.NtroUserMessage;

public class UpdateUserInfoMessage extends NtroUserMessage<User> {
	
	private String screenName;

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
}
