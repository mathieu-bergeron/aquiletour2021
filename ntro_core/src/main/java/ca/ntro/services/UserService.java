package ca.ntro.services;

import ca.ntro.users.NtroUser;

public abstract class UserService {

	private NtroUser user;

	public NtroUser getUser() {
		if(user == null) {
			user = new NtroUser();
			user.setId("__ntro");
			user.setAuthToken("__ntro");
		}

		return user;
	}

	public void registerCurrentUser(NtroUser user) {
		this.user = user;
	}

}
