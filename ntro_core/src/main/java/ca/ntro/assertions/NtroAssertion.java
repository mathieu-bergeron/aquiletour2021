package ca.ntro.assertions;

import ca.ntro.core.introspection.ClassSignature;

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

	public ClassSignatureExpression thatClassSignature(ClassSignature classSignature) {
		
		ClassSignatureExpression classSignatureExpression = new ClassSignatureExpression(this, classSignature);
		
		addSubExpression(classSignatureExpression);

		return classSignatureExpression;
	}

	public ObjectExpression thatObject(Object object) {
		
		ObjectExpression objectExpression = new ObjectExpression(this, object);
		
		addSubExpression(objectExpression);

		return objectExpression;
	}
}
