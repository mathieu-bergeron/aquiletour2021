package ca.ntro.core.mvc;

import ca.ntro.core.Path;
import ca.ntro.core.system.trace.T;

public class NtroContext {
	
	private String lang;
	private String userId;
	private String authToken;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public boolean hasDifferentLang(NtroContext otherContext) {
		return !this.lang.equals(otherContext.getLang());
	}
}
