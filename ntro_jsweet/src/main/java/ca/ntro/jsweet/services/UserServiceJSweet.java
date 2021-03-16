package ca.ntro.jsweet.services;

import ca.ntro.services.Ntro;
import ca.ntro.services.UserService;
import ca.ntro.users.NtroUser;

import static def.es6.Globals.Cookies;
import static def.es6.Globals.decodeURI;

public class UserServiceJSweet extends UserService {

	@Override
	public NtroUser currentUser() {

		String urlEncodedUserString = Cookies.get("user");
		NtroUser user = null;
		
		if(urlEncodedUserString != null) {
			String userString = decodeURI(urlEncodedUserString);
			user = Ntro.jsonService().fromString(NtroUser.class, userString);
		}
		
		if(user == null) {
			user = new NtroUser();
			user.setId("__anon");
			user.setAuthToken("__anonToken");
		}

		return user;
	}

}
