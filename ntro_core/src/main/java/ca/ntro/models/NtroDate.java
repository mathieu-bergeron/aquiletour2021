package ca.ntro.models;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class NtroDate implements NtroModelValue {
	
	private long epochSeconds = -1;

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
	
	public boolean isDefined() {
		return epochSeconds >= 0;
	}

	public boolean biggerThan(NtroDate other) {
		T.call(this);
		
		if(other == null || !other.isDefined()) {

			return true;

		}else {
			
			return epochSeconds > other.epochSeconds;
		}
	}

	public void adjustTime(NtroTimeOfDay time) {
		T.call(this);

		Ntro.calendar().setTimeOfDay(this, time);
	}
}
