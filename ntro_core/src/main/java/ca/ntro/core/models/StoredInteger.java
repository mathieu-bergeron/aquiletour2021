package ca.ntro.core.models;

import ca.ntro.core.system.trace.T;

public class StoredInteger extends StoredProperty<Integer> {

	public StoredInteger() {
		super();
		T.call(this);
	}

	public StoredInteger(Integer value) {
		super(value);
		T.call(this);
	}

}
