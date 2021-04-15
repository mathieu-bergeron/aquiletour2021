package ca.ntro.models;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.services.Ntro;

public class NtroDate implements NtroModelValue {
	
	private long epochSeconds;

	public NtroDate() {
	}

	public NtroDate(long epochSeconds) {
		this.epochSeconds = epochSeconds;
	}

	public NtroDate deltaDays(int i) {
		return new NtroDate(epochSeconds + i*60*60*24);
	}

	public long getEpochSeconds() {
		return epochSeconds;
	}

	public void setEpochSeconds(long epochSeconds) {
		this.epochSeconds = epochSeconds;
	}

	public String format(String dateFormat) {
		return Ntro.calendar().format(epochSeconds, dateFormat);
	}
}
