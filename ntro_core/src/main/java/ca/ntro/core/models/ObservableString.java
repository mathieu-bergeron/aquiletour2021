package ca.ntro.core.models;

import ca.ntro.core.system.trace.T;

public class ObservableString extends ObservableProperty<String> {

	public ObservableString() {
		super();
		T.call(this);
	}

	public ObservableString(String value) {
		super(value);
		T.call(this);
	}

}
