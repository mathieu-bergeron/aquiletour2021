package ca.aquiletour.core.messages.user;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.users.User;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.NtroUserMessage;

public class UserSendsLoginCodeMessage extends NtroUserMessage<User> {
	
	private String loginCode;
	private List<NtroMessage> delayedMessages = new ArrayList<>();

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public List<NtroMessage> getDelayedMessages() {
		return delayedMessages;
	}

	public void setDelayedMessages(List<NtroMessage> delayedMessages) {
		this.delayedMessages = delayedMessages;
	}
}
