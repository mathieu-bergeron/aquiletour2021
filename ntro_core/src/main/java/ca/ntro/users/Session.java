package ca.ntro.users;

import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.models.NtroModel;

public class Session<U extends NtroUser, D extends JsonSerializable> implements NtroModel {
	
	private U user = (U) new NtroUser();
	private D sessionData = (D) new JsonSerializable() {};
	private String lang = "";
	private String loginCode = "";
	private long timeToLiveMiliseconds = 1000 * 60 * 1;         // TMP: 1 minute by default
	
	public U getUser() {
		return user;
	}

	public void setUser(U user) {
		this.user = user;
	}

	public long getTimeToLiveMiliseconds() {
		return timeToLiveMiliseconds;
	}

	public void setTimeToLiveMiliseconds(long timeToLiveMiliseconds) {
		this.timeToLiveMiliseconds = timeToLiveMiliseconds;
	}

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public D getSessionData() {
		return sessionData;
	}

	public void setSessionData(D sessionData) {
		this.sessionData = sessionData;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
	
	

	
}
