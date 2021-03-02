package ca.ntro.assertions;

public class IsTrueExpression extends AssertExpression {
	
	private ValueExpression<Boolean> valueExpression;

	public IsTrueExpression(ValueExpression<Boolean> valueExpression) {
		this.valueExpression = valueExpression;
	}

	@Override
	public String failMessage() {
		boolean result = valueExpression.evaluate();
		
		if(!result) {
			
			return "isTrue(false)";

		}else {
			
			return null;
		}
	}
}
