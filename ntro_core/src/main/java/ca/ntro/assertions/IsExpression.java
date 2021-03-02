package ca.ntro.assertions;

import ca.ntro.core.system.log.Log;

public class IsExpression extends AssertExpression {
	
	private ValueExpression valueExpression;
	private Object otherValue;

	public IsExpression(ValueExpression valueExpression, Object otherValue) {
		this.valueExpression = valueExpression;
		this.otherValue = otherValue;
	}
	
	void verify(){
		if(shouldFail()) {
			Log.fatalError("Assertion failed: " + valueExpression.toString());
		}
	}
	
	public boolean shouldFail() {
		Object thisValue = valueExpression.evaluate();
		
		if(thisValue == null) return otherValue == null;
		
		return !thisValue.equals(otherValue);
	}

	@Override
	public String failMessage() {
		if(shouldFail()) {
			
			return "is(TODO)";
			
		}

		return null;
	}


}
