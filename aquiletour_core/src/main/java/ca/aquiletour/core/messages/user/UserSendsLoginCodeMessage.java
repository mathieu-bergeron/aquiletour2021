package ca.aquiletour.core.messages.user;

public class UserSendsLoginCodeMessage extends DelayedMessagesMessage {
	
	private String loginCode;

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}
}
