package ca.ntro.assertions;

import ca.ntro.core.system.log.Log;

public class IsTrueExpression extends AssertExpression {
	
	private AssertExpression parentExpression;
	private Object value;

	public IsTrueExpression(AssertExpression parentExpression, Object value) {
		this.parentExpression = parentExpression;
		this.value = value;
	}

	@Override
	void verify(){
		if(shouldFail()) {
			Log.fatalError("Assertion failed: " + parentExpression.toString());
		}
	}
	
	private boolean shouldFail() {
		if(value == null) return true;
		
		return !value.equals(true);
	}
	
	

}
