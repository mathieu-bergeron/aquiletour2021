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

	public NtroDate deltaSeconds(long i) {
		return new NtroDate(epochSeconds + i);
	}

	public NtroDate deltaMinutes(int i) {
		return deltaSeconds(i*60);
	}

	public NtroDate deltaHours(int i) {
		return deltaMinutes(i*60);
	}

	public NtroDate deltaDays(int i) {
		return deltaHours(i*24);
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

	public boolean smallerThan(NtroDate other) {
		T.call(this);

		if(other == null || !other.isDefined()) {

			return false;

		}else {
			
			return epochSeconds < other.epochSeconds;
		}
	}

	public void adjustTime(NtroTimeOfDay time) {
		T.call(this);

		Ntro.calendar().setTimeOfDay(this, time);
	}



}
