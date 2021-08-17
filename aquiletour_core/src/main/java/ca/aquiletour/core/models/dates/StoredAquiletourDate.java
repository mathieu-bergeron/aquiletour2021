package ca.aquiletour.core.models.dates;

import ca.ntro.core.models.StoredProperty;
import ca.ntro.core.system.trace.T;

public class StoredAquiletourDate extends StoredProperty<AquiletourDate> {
	
	public StoredAquiletourDate() {
		super(AquiletourDate.undefined());
		T.call(this);
	}

	public StoredAquiletourDate(AquiletourDate date) {
		super(date);
		T.call(this);
	}
}
