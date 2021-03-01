package ca.ntro.assertions;

public class EmptyExpression extends AssertExpression {

	private AssertExpression parentExpression;
	
	public EmptyExpression(AssertExpression parentExpression) {
		this.parentExpression = parentExpression;
	}

	public void isTrue(Object value) {
		parentExpression.addSubExpression(new IsTrueExpression(parentExpression, value));
		parentExpression.verify();
	}
	
	
}
