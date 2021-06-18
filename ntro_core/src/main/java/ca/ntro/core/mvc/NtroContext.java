package ca.ntro.core.mvc;

import ca.ntro.users.NtroSessionData;
import ca.ntro.users.NtroUser;

public class NtroContext<U extends NtroUser, S extends NtroSessionData>{
	
	private boolean isSocketOpen = false;
	private String lang;
	private NtroSessionData sessionData;

	// FIXME: authToken should be here
	//        instead of User
	//private String authToken;

	private NtroUser user;
	
	
	public boolean isSocketOpen() {
		return isSocketOpen;
	}
	
	public void updateIsSocketOpen(boolean isSocketOpen) {
		this.isSocketOpen = isSocketOpen;
	}
	
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

	public void registerSessionData(NtroSessionData sessionData) {
		this.sessionData = sessionData;
	}

	@SuppressWarnings("unchecked")
	public S sessionData() {
		return (S) sessionData;
	}
}
