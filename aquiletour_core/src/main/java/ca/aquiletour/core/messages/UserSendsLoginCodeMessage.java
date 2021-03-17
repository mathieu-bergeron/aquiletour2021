package ca.aquiletour.core.messages;

import ca.ntro.messages.NtroUserMessage;

public class UserSendsLoginCodeMessage extends NtroUserMessage {
	
	private String loginCode;

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}
}
