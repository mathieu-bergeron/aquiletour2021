package ca.ntro.assertions;

public abstract class ValueExpression<O extends Object> extends SimpleAssertExpression {
	
	public abstract O evaluate();

}
