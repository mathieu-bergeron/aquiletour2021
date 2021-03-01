package ca.ntro.assertions;

import ca.ntro.core.system.log.Log;

public class NtroAssertion {
	
	private Object value;
	
	public NtroAssertion that(Object value) {
		return this;
	}

	public boolean isTrue() {
		return value.equals(true);
	}

	public void isNot(Object otherValue) {
		if(isEqual(otherValue)) {
			Log.fatalError("Assertion failed " + toString());
		}
	}
	
	private boolean isEqual(Object otherValue) {
		if(otherValue == null) return value != null;
		return otherValue.equals(value);
	}

	public void is(Object otherValue) {
		if(!isEqual(otherValue)) {
			Log.fatalError("Assertion failed " + toString());
		}
	}

}
