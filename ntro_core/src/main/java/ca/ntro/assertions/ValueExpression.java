package ca.ntro.assertions;

public class ValueExpression extends AssertExpression {

	private AssertExpression parentExpression;
	private Object value;
	
	public ValueExpression(AssertExpression parentExpression, Object value) {
		this.parentExpression = parentExpression;
		this.value = value;
	}

	public void isNot(Object otherValue) {
		parentExpression.addSubExpression(new IsNotExpression(this, otherValue));
		parentExpression.verify();
	}

	public void is(Object otherValue) {
		parentExpression.addSubExpression(new IsExpression(this, otherValue));
		parentExpression.verify();
	}

	@Override
	void verify() {
	}

	public Object evaluate() {
		return value;
	}
	
	@Override
	public String toString() {
		return parentExpression.toString();
	}

}
