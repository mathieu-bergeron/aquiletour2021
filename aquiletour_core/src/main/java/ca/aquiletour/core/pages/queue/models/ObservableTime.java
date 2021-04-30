package ca.aquiletour.core.pages.queue.models;

import ca.ntro.core.models.StoredProperty;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;

public class ObservableTime extends StoredProperty<NtroDate> {
	
	public ObservableTime() {
		super(new NtroDate());
		T.call(this);
	}

	public ObservableTime(NtroDate date) {
		super(date);
		T.call(this);
	}
}
