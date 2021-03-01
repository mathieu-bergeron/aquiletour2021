package ca.ntro.assertions;

public class NtroAssertion extends AssertExpression {
	
	public ValueExpression that(Object value) {
		
		ValueExpression valueExpression = new ValueExpression(this, value);
		
		addSubExpression(valueExpression);

		return valueExpression;
	}

	public EmptyExpression that() {
		
		EmptyExpression emptyExpression = new EmptyExpression(this);
		
		addSubExpression(emptyExpression);

		return emptyExpression;
	}
}
