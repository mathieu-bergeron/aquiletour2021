package ca.ntro.assertions;

import ca.ntro.core.introspection.NtroClass;
import ca.ntro.services.Ntro;

public class DoesImplementExpression extends SimpleAssertExpression {

	private NtroClassExpression classSignatureExpression;
	private Class<?> _interface;

	public DoesImplementExpression(NtroClassExpression classSignatureExpression, Class<?> _interface) {
		this.classSignatureExpression = classSignatureExpression;
		this._interface = _interface;
	}

	@Override
	public String failMessage() {
		NtroClass classSignature = classSignatureExpression.evaluate();
		
		if(!classSignature.ifImplements(_interface)) {
			
			StringBuilder builder = new StringBuilder();
			
			builder.append("that(");
			builder.append(classSignature.simpleName());
			builder.append(").doesImplement(");
			builder.append(Ntro.introspector().ntroClassFromJavaClass(_interface).simpleName());
			builder.append(")");

			return builder.toString();
		}

		return null;
	}

}
