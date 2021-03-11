package ca.ntro.core.models;

import ca.ntro.core.system.trace.T;

public class ObservableValue<M extends NtroModelValue> extends ObservableProperty<M> {
	
	public ObservableValue() {
		super();
		T.call(this);
	}

	public ObservableValue(M value) {
		super(value);
		T.call(this);
	}

}
