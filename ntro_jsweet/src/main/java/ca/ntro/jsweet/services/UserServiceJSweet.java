package ca.ntro.jsweet.services;

import ca.ntro.core.NtroUser;
import ca.ntro.services.Ntro;
import ca.ntro.services.UserService;
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

		return user;
	}

}
