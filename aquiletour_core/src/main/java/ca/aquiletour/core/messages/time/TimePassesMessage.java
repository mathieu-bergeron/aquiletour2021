package ca.aquiletour.core.messages.time;

import ca.ntro.messages.NtroMessage;
import ca.ntro.models.NtroDate;

public class TimePassesMessage extends NtroMessage {
	
	private long elapsedTimeSeconds;
	private NtroDate currentTime = new NtroDate();

	public long getElapsedTimeSeconds() {
		return elapsedTimeSeconds;
	}
	public void setElapsedTimeSeconds(long elapsedTimeSeconds) {
		this.elapsedTimeSeconds = elapsedTimeSeconds;
	}
	public NtroDate getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(NtroDate currentTime) {
		this.currentTime = currentTime;
	}
}
