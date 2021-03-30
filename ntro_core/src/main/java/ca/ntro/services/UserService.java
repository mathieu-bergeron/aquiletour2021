package ca.ntro.services;

import ca.ntro.users.NtroUser;

public abstract class UserService {
	
	private NtroUser _user;

	public NtroUser user() {
		if(_user == null) {
			_user = new NtroUser();
			_user.setId("__ntro");
			_user.setAuthToken("__ntro");
		}

		return _user;
	}

	public void registerCurrentUser(NtroUser user) {
		this._user = user;
	}
	
}
