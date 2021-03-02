package ca.ntro.assertions;

import ca.ntro.core.Ntro;
import ca.ntro.core.introspection.ClassSignature;

public class IsInstanceOfExpression extends SimpleAssertExpression {
	
	private ClassSignatureExpression classSignatureExpression;
	private Class<?> classOrInterface;

	public IsInstanceOfExpression(ClassSignatureExpression classSignatureExpression, Class<?> classOrInterface) {
		this.classSignatureExpression = classSignatureExpression;
		this.classOrInterface = classOrInterface;
	}

	@Override
	public String failMessage() {
		ClassSignature classSignature = classSignatureExpression.evaluate();

		if(!classSignature.ifInstanceOf(classOrInterface)) {
			
			StringBuilder builder = new StringBuilder();
			
			builder.append("that(");
			builder.append(classSignature.simpleName());
			builder.append(").isInstanceOf(");
			builder.append(Ntro.introspector().classSignatureForClass(classOrInterface).simpleName());
			builder.append(")");

			return builder.toString();
		}

		return null;
	}

}
