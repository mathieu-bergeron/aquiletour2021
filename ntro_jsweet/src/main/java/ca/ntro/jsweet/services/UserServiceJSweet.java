package ca.ntro.jsweet.services;

import ca.ntro.core.NtroUser;
import ca.ntro.jsweet.Globals.Cookies;
import ca.ntro.services.UserService;

public class UserServiceJSweet extends UserService {

	@Override
	public NtroUser currentUser() {
		
		String userId = Cookies.get("userId");
		String authToken = Cookies.get("authToken");
		
		NtroUser user = new NtroUser();
		user.setId(userId);
		user.setAuthToken(authToken);
		
		return user;
	}

}
