package ca.ntro.assertions;

public class IsNotExpression extends IsExpression {
	
	public IsNotExpression(ValueExpression valueExpression, Object otherValue) {
		super(valueExpression, otherValue);
	}
	
	@Override
	public boolean shouldFail() {
		return !super.shouldFail();
	}

}
