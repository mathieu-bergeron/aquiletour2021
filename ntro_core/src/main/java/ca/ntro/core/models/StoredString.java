package ca.ntro.core.models;

import ca.ntro.core.system.trace.T;

public class StoredString extends StoredProperty<String> {

	public StoredString() {
		super("");
		T.call(this);
	}

	public StoredString(String value) {
		super(value);
		T.call(this);
	}

}
