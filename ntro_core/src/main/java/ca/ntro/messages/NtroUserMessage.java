package ca.ntro.messages;

import ca.ntro.users.NtroUser;

public class NtroUserMessage<U extends NtroUser> extends NtroMessage {
	
	private U user;
	private String authToken;

	public U getUser() {
		return user;
	}

	public void setUser(U user) {
		this.user = user;
		setAuthToken(user.getAuthToken());
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
}
