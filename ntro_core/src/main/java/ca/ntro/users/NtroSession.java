package ca.ntro.users;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.NtroModelValue;

public class NtroSession implements NtroModel {

	// FIXME: authToken should be here
	//        instead of User
	//private String authToken = "";

	private String lang = "";
	
	private NtroUser user = new NtroUser();
	private NtroSessionData sessionData = new NtroSessionData();

	private long timeToLiveMiliseconds = 1000 * 60 * 1;         // TMP: 1 minute by default
	
	public NtroUser getUser() {
		return user;
	}

	public void setUser(NtroUser user) {
		this.user = user;
	}

	public long getTimeToLiveMiliseconds() {
		return timeToLiveMiliseconds;
	}

	public void setTimeToLiveMiliseconds(long timeToLiveMiliseconds) {
		this.timeToLiveMiliseconds = timeToLiveMiliseconds;
	}

	public NtroSessionData getSessionData() {
		return sessionData;
	}

	public void setSessionData(NtroSessionData sessionData) {
		this.sessionData = sessionData;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	/*
	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}*/
	
	
	
}
