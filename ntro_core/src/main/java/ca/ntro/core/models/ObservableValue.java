package ca.ntro.core.models;

import ca.ntro.core.system.trace.T;

public class ObservableValue<O extends Object> extends ObservableProperty<O> {
	
	public ObservableValue() {
		super();
		T.call(this);
	}

	public ObservableValue(O value) {
		super(value);
		T.call(this);
	}

}
