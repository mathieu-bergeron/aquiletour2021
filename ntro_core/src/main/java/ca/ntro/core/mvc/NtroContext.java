package ca.ntro.core.mvc;

import ca.ntro.core.Path;
import ca.ntro.core.system.trace.T;

public class NtroContext {
	
	private String lang;
	private String userId;
	private String authToken;
	private Path path;
	
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

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public boolean hasSamePath(NtroContext previousContext) {
		T.call(this);

		return this.path.equals(previousContext.getPath());
	}
}
