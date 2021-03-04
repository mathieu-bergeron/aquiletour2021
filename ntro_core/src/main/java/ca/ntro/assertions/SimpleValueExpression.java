package ca.ntro.assertions;

public class SimpleValueExpression extends ValueExpression<Object> {

	private Object value;
	
	public SimpleValueExpression(Object value) {
		this.value = value;
	}

	public IsNotExpression isNot(Object otherValue) {

		IsNotExpression result = new IsNotExpression(this, otherValue);
		
		setChild(result);
		
		return result;
	}

	public IsExpression is(Object otherValue) {

		IsExpression result = new IsExpression(this, otherValue);
		
		setChild(result);
		
		return result;
	}

	public IsTrueExpression isTrue() {

		IsTrueExpression result = new IsTrueExpression(this);
		
		setChild(result);
		
		return result;
	}

	@Override
	public Object evaluate() {
		return value;
	}
}
