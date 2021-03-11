package ca.ntro.core.models;

import ca.ntro.core.system.trace.T;

public class ObservableBoolean extends ObservableProperty<Boolean> {

	public ObservableBoolean() {
		super();
		T.call(this);
	}

	public ObservableBoolean(Boolean value) {
		super(value);
		T.call(this);
	}

}
