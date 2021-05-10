package ca.aquiletour.core.messages.user;

public class UserSendsPassword extends DelayedMessagesMessage {
	
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
