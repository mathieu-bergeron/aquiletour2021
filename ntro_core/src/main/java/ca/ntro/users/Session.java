package ca.ntro.users;

import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.models.NtroModel;

public class Session<U extends NtroUser, D extends JsonSerializable> implements NtroModel {
	
	private U user = (U) new NtroUser();
	private D appData = (D) new JsonSerializable() {};
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

	public D getAppData() {
		return appData;
	}

	public void setAppData(D appData) {
		this.appData = appData;
	}
	
}
