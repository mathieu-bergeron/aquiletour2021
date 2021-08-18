package ca.ntro.users;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.services.Ntro;

public class NtroSession implements NtroModel {

	// FIXME: authToken should be here
	//        instead of User
	//private String authToken = "";

	private String lang = "";
	
	private NtroUser user = new NtroUser();
	private NtroSessionData sessionData = new NtroSessionData();
	
	private NtroDate expiryDate = new NtroDate();

	public NtroUser getUser() {
		return user;
	}

	public void setUser(NtroUser user) {
		this.user = user;
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

	public NtroDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(NtroDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public boolean hasExpired() {
		T.call(this);

		return Ntro.calendar().now().biggerThan(getExpiryDate());
	}
	
	public NtroSession clone() {
		T.call(this);
		
		NtroSession clone = Ntro.factory().newInstance(getClass());
		
		clone.deepCopyOf(this);

		return clone;
	}

	protected void deepCopyOf(NtroSession toCopy) {
		T.call(this);

		setLang(toCopy.getLang());
		setUser(toCopy.getUser().clone());
		setSessionData(toCopy.getSessionData().clone());
		setExpiryDate(toCopy.getExpiryDate().clone());
	}
}
