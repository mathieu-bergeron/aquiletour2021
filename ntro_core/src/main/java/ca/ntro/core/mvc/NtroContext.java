package ca.ntro.core.mvc;

import ca.ntro.users.NtroSessionData;
import ca.ntro.users.NtroUser;

public class NtroContext<U extends NtroUser, S extends NtroSessionData>{
	
	private String lang;
	private S sessionData;

	// FIXME: authToken should be here
	//        instead of User
	//private String authToken;

	private NtroUser user;
	
	public String lang() {
		return lang;
	}


	public void registerLang(String lang) {
		this.lang = lang;
	}
	
	/*
	public String authToken() {
		return authToken;
	}

	public void registerAuthToken(String authToken) {
		this.authToken = authToken;
	}*/

	@SuppressWarnings("unchecked")
	public U user() {
		return (U) user;
	}

	public void registerUser(NtroUser user) {
		this.user = user;
	}

	public boolean hasSameLang(NtroContext<U,S> otherContext) {
		if(lang == null && otherContext.lang != null) return false;
		return this.lang.equals(otherContext.lang);
	}

	public void registerSessionData(S sessionData) {
		this.sessionData = sessionData;
	}

	public S sessionData() {
		return sessionData;
	}
}
