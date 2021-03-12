package ca.ntro.services;

import ca.ntro.core.NtroUser;

public abstract class UserService {
	
	private NtroUser currentUser;

	public NtroUser currentUser() {
		if(currentUser == null) {
			currentUser = new NtroUser();
			currentUser.setId("__anon");
			currentUser.setAuthToken("__anonToken");
		}

		return currentUser;
	}

	public void registerCurrentUser(NtroUser user) {
		this.currentUser = user;
	}
	
}
