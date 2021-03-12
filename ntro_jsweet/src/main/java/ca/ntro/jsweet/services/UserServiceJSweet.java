package ca.ntro.jsweet.services;

import ca.ntro.core.NtroUser;
import ca.ntro.services.UserService;
import static def.es6.Globals.Cookies;

public class UserServiceJSweet extends UserService {

	@Override
	public NtroUser currentUser() {
		
		String userId = Cookies.get("userId");
		String authToken = Cookies.get("authToken");
		
		System.out.println("userId: " + userId);
		System.out.println("authToken: " + authToken);
		
		NtroUser user = new NtroUser();
		user.setId(userId);
		user.setAuthToken(authToken);
		
		return user;
	}

}
