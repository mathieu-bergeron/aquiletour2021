package ca.ntro.jsweet.services;

import ca.ntro.services.Ntro;
import ca.ntro.services.UserService;
import ca.ntro.users.NtroUser;

import static def.es6.Globals.Cookies;
import static def.es6.Globals.decodeURI;
import static def.es6.Globals.encodeURI;

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
			user.setId("__ntro");
			user.setAuthToken("__ntro");
		}

		return user;
	}

	@Override
	public void registerCurrentUser(NtroUser user) {
		super.registerCurrentUser(user);
		
		String userString = Ntro.jsonService().toString(user);
		userString = userString.replace(" ", "");

		String urlEncodedUserString = encodeURI(userString);
		
		Cookies.set("user", urlEncodedUserString, null);
	}

}
