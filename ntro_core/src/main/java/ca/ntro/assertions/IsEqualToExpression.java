package ca.ntro.assertions;

import java.util.List;
import java.util.Map;

import ca.ntro.core.services.NtroCollections;
import ca.ntro.core.system.log.Log;

public class IsEqualToExpression extends AssertExpression {
	
	private ValueExpression valueExpression;
	private Object otherValue;

	public IsEqualToExpression(ValueExpression valueExpression, Object otherValue) {
		this.valueExpression = valueExpression;
		this.otherValue = otherValue;
	}
	
	public boolean shouldFail() {
		Object thisValue = valueExpression.evaluate();

		if(thisValue == null) return otherValue != null;
		
		if(thisValue == otherValue) return false;
		
		if(thisValue instanceof List) {
			return !NtroCollections.listEquals((List<?>)thisValue, (List<?>)otherValue);
		}
		
		if(thisValue instanceof Map) {
			return !NtroCollections.mapEquals((Map<?,?>)thisValue, (Map<?,?>)otherValue);
		}
		
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
