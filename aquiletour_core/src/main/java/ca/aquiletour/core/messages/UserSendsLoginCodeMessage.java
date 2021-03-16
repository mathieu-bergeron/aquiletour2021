package ca.aquiletour.core.messages;

import ca.ntro.messages.NtroMessage;

public class UserSendsLoginCodeMessage extends NtroMessage {
	
	private String loginCode;

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}
}
