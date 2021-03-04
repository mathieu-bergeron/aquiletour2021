package ca.ntro.assertions;

public class IsTrueExpression extends AssertExpression {
	
	private ValueExpression valueExpression;

	public IsTrueExpression(ValueExpression valueExpression) {
		this.valueExpression = valueExpression;
	}

	@Override
	public String failMessage() {
		Object result = valueExpression.evaluate();
		
		if(result instanceof Boolean || result.getClass().equals(boolean.class)) {
			
			boolean isOk = (boolean) result;
			
			if(isOk) {
				
				return null;
				
				
			}else {
				
				return "that(false).isTrue()";
			}
			
		}else {
			
			return "isTrue called on non-boolean";
			
		}
	}
}
