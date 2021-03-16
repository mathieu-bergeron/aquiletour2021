package ca.ntro.core.mvc;

import ca.ntro.users.NtroUser;

public class NtroContext<U extends NtroUser>{
	
	private String lang;
	private NtroUser user;
	
	public String lang() {
		return lang;
	}
	public void registerLang(String lang) {
		this.lang = lang;
	}
	@SuppressWarnings("unchecked")
	public U user() {
		return (U) user;
	}
	public void registerUser(NtroUser user) {
		this.user = user;
	}

	public boolean hasSameLang(NtroContext<U> otherContext) {
		if(lang == null && otherContext.lang != null) return false;
		return this.lang.equals(otherContext.lang);
	}
}
