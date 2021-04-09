package ca.ntro.assertions;

import ca.ntro.core.introspection.NtroClass;
import ca.ntro.services.Ntro;

public class IsInstanceOfExpression extends SimpleAssertExpression {
	
	private NtroClassExpression classSignatureExpression;
	private Class<?> classOrInterface;

	public IsInstanceOfExpression(NtroClassExpression classSignatureExpression, Class<?> classOrInterface) {
		this.classSignatureExpression = classSignatureExpression;
		this.classOrInterface = classOrInterface;
	}

	@Override
	public String failMessage() {
		NtroClass classSignature = classSignatureExpression.evaluate();

		if(!classSignature.ifInstanceOf(classOrInterface)) {
			
			StringBuilder builder = new StringBuilder();
			
			builder.append("that(");
			builder.append(classSignature.simpleName());
			builder.append(").isInstanceOf(");
			builder.append(Ntro.introspector().ntroClassFromJavaClass(classOrInterface).simpleName());
			builder.append(")");

			return builder.toString();
		}

		return null;
	}

}
