package ca.aquiletour.core.pages.queue.models;

import ca.ntro.core.models.StoredProperty;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;

public class StoredTime extends StoredProperty<NtroDate> {
	
	public StoredTime() {
		super(new NtroDate());
		T.call(this);
	}

	public StoredTime(NtroDate date) {
		super(date);
		T.call(this);
	}

	public void incrementBySeconds(int incrementSeconds) {
		T.call(this);
		
		setValue(getValue().deltaSeconds(incrementSeconds));
	}
}
