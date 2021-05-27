package ca.aquiletour.core.messages.user;

public class UserSendsPasswordMessage extends DelayedMessagesMessage {
	
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
