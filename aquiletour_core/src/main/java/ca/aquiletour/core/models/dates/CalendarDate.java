package ca.aquiletour.core.models.dates;

import ca.ntro.core.models.NtroModelValue;

public class CalendarDate implements NtroModelValue {
	
	private long epochTime;

	public long getEpochTime() {
		return epochTime;
	}

	public void setEpochTime(long epochTime) {
		this.epochTime = epochTime;
	}

}
