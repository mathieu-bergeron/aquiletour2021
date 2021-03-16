package ca.ntro.users;

import ca.ntro.core.models.NtroModel;

public class Session implements NtroModel {
	
	private NtroUser user = new NtroUser();
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
}
