package ca.ntro.core.models;

import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class StoredLong extends StoredProperty<Long> {

	public StoredLong() {
		super(0l);
		T.call(this);
	}

	public StoredLong(Long value) {
		super(value);
		T.call(this);
	}

	// FIXME: type parameter <Integer> not supported in Json deserialization
	//        value is actually a Double
	@Override
	public Long getValue() {
		T.call(this);
		
		return (Long) Ntro.introspector().castPrimitiveValue(Long.class, super.getValue());
	}

}
