package ca.ntro.core.mvc;

import ca.ntro.core.NtroUser;

public class NtroContext<U extends NtroUser>{
	
	private String lang;
	private U user;
	
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public U getUser() {
		return user;
	}
	public void setUser(U user) {
		this.user = user;
	}

	public boolean hasSameLang(NtroContext<U> otherContext) {
		if(lang == null && otherContext.lang != null) return false;
		return this.lang.equals(otherContext.lang);
	}
}
