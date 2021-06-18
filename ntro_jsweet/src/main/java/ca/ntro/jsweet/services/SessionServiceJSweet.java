package ca.ntro.jsweet.services;

import ca.ntro.services.Ntro;
import ca.ntro.services.SessionService;
import ca.ntro.users.NtroSession;

import static def.es6.Globals.Cookies;
import static def.es6.Globals.decodeURI;
import static def.es6.Globals.encodeURI;

public class SessionServiceJSweet extends SessionService {

	@Override
	public NtroSession session() {

		String urlEncodedSessionString = Cookies.get("session");
		NtroSession session = null;
		
		if(urlEncodedSessionString != null) {
			String sessionString = decodeURI(urlEncodedSessionString);
			session = Ntro.jsonService().fromString(NtroSession.class, sessionString);
		}
		
		if(session == null) {
			session = new NtroSession();
		}

		return session;
	}

	@Override
	public void registerCurrentSession(NtroSession session) {
		super.registerCurrentSession(session);
		
		String sessionString = Ntro.jsonService().toString(session);

		//sessionString = sessionString.replace(" ", "");
		//sessionString = encodeURI(sessionString);
		
		def.js.Object options = new def.js.Object();
		options.$set("sameSite", "strict");
		options.$set("secure", "true");
		options.$set("max-age", String.valueOf(60*60*24*30*4)); // 4 months
		
		Cookies.set("session", sessionString, options);
	}

}
