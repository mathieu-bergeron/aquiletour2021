package ca.ntro.services;

import ca.ntro.users.NtroUser;

public abstract class UserService {
	
	private NtroUser user;

	public NtroUser currentUser() {
		if(user == null) {
			user = new NtroUser();
			user.setId("__anon");
			user.setAuthToken("__anonToken");
		}

		return user;
	}

	public void registerCurrentUser(NtroUser user) {
		this.user = user;
	}
	
}
