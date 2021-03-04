package ca.ntro.assertions;

import ca.ntro.core.system.log.Log;

public class IsExpression extends AssertExpression {
	
	private ValueExpression valueExpression;
	private Object otherValue;

	public IsExpression(ValueExpression valueExpression, Object otherValue) {
		this.valueExpression = valueExpression;
		this.otherValue = otherValue;
	}
	
	public boolean shouldFail() {
		Object thisValue = valueExpression.evaluate();

		if(thisValue == null) return otherValue != null;
		
		if(thisValue == otherValue) return false;
		
		return !thisValue.equals(otherValue);
	}

	@Override
	public String failMessage() {
		Object thisValue = valueExpression.evaluate();

		if(shouldFail()) {
			
			return "that("+thisValue+").is("+ otherValue +")";
			
		}

		return null;
	}


}
