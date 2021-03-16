package ca.ntro.core.mvc;

import ca.ntro.users.NtroUser;

public class NtroContext<U extends NtroUser>{
	
	private String lang;
	private U user;
	
	public String lang() {
		return lang;
	}
	public void registerLang(String lang) {
		this.lang = lang;
	}
	public U user() {
		return user;
	}
	public void registerUser(U user) {
		this.user = user;
	}

	public boolean hasSameLang(NtroContext<U> otherContext) {
		if(lang == null && otherContext.lang != null) return false;
		return this.lang.equals(otherContext.lang);
	}
}
