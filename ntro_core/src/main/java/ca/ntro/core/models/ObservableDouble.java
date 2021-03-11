package ca.ntro.core.models;

import ca.ntro.core.system.trace.T;

public class ObservableDouble extends ObservableProperty<Double> {

	public ObservableDouble() {
		super();
		T.call(this);
	}

	public ObservableDouble(double value) {
		super(value);
		T.call(this);
	}

}
