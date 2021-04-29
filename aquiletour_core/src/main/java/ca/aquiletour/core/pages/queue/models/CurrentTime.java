package ca.aquiletour.core.pages.queue.models;

import ca.ntro.core.models.StoredProperty;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;

public class CurrentTime extends StoredProperty<NtroDate> {
	
	public CurrentTime() {
		super(new NtroDate());
		T.call(this);
	}

	public CurrentTime(NtroDate date) {
		super(date);
		T.call(this);
	}
}
