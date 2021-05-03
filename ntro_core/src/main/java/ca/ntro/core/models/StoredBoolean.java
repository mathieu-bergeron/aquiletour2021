package ca.ntro.core.models;

import ca.ntro.core.system.trace.T;

public class StoredBoolean extends StoredProperty<Boolean> {

	public StoredBoolean() {
		super(false);
		T.call(this);
	}

	public StoredBoolean(Boolean value) {
		super(value);
		T.call(this);
	}


}
