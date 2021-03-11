package ca.ntro.core.models;

import ca.ntro.core.system.trace.T;

public class StoredDouble extends StoredProperty<Double> {

	public StoredDouble() {
		super();
		T.call(this);
	}

	public StoredDouble(double value) {
		super(value);
		T.call(this);
	}

}
