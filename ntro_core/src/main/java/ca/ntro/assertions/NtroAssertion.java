package ca.ntro.assertions;

import ca.ntro.core.services.ValueFormatter;
import ca.ntro.core.system.log.Log;

public class NtroAssertion {
	
	private Object value;

	private StringBuilder exprBuilder = new StringBuilder();
	
	public NtroAssertion that(Object value) {
		exprBuilder.append(".that(");
		ValueFormatter.format(exprBuilder, value);
		exprBuilder.append(")");

		return this;
	}

	public boolean isTrue() {
		exprBuilder.append(".isTrue()");

		return value.equals(true);
	}

	public void isNot(Object otherValue) {
		exprBuilder.append(".isNot(");
		ValueFormatter.format(exprBuilder, otherValue);
		exprBuilder.append(")");

		if(isEqual(otherValue)) {
			Log.fatalErrorAtDepth("Unable to verify " + this.toString(), 2);
		}
	}
	
	private boolean isEqual(Object otherValue) {
		if(otherValue == null) return value != null;
		return otherValue.equals(value);
	}

	public void is(Object otherValue) {
		exprBuilder.append(".is(");
		ValueFormatter.format(exprBuilder, otherValue);
		exprBuilder.append(")");
		
		if(!isEqual(otherValue)) {
			Log.fatalErrorAtDepth("Unable to verify " + this.toString(), 2);
		}
	}
	
	@Override
	public String toString() {
		return exprBuilder.toString();
	}


}
