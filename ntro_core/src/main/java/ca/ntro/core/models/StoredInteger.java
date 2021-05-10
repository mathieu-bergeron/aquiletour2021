package ca.ntro.core.models;

import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class StoredInteger extends StoredProperty<Integer> {

	public StoredInteger() {
		super(0);
		T.call(this);
	}

	public StoredInteger(Integer value) {
		super(value);
		T.call(this);
	}

	// FIXME: type parameter <Integer> not supported in Json deserialization
	//        value is actually a Double
	@Override
	public Integer getValue() {
		T.call(this);
		
		return (Integer) Ntro.introspector().castPrimitiveValue(Integer.class, super.getValue());
	}

	public void incrementBy(int increment) {
		T.call(this);
		
		setValue(getValue() + increment);
	}

}
