package ca.ntro.assertions;

public class SimpleValueExpression extends ValueExpression<Object> {

	private Object value;
	
	public SimpleValueExpression(Object value) {
		this.value = value;
	}

	public IsNotEqualToExpression isNotEqualTo(Object otherValue) {

		IsNotEqualToExpression result = new IsNotEqualToExpression(this, otherValue);
		
		setChild(result);
		
		return result;
	}

	public IsEqualToExpression isEqualTo(Object otherValue) {

		IsEqualToExpression result = new IsEqualToExpression(this, otherValue);
		
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
