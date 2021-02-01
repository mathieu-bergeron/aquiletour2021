package ca.ntro.core.models.properties.observable.simple;

import ca.ntro.core.models.properties.NtroModelValue;
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

	@Override
	protected Class<?> getValueType() {
		T.call(this);
		return NtroModelValue.class;
	}
}
