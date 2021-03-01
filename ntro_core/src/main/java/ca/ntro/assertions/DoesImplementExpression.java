package ca.ntro.assertions;

import ca.ntro.core.introspection.ClassSignature;
import ca.ntro.core.system.log.Log;

public class DoesImplementExpression extends AssertExpression {

	private ClassSignatureExpression classSignatureExpression;
	private Class<?> _interface;

	public DoesImplementExpression(ClassSignatureExpression classSignatureExpression, Class<?> _interface) {
		this.classSignatureExpression = classSignatureExpression;
		this._interface = _interface;
	}

	@Override
	void verify() {
		ClassSignature classSignature = classSignatureExpression.evaluate();

		if(!classSignature.ifImplements(_interface)) {
			Log.fatalError("Assertion failed: " + classSignatureExpression.toString());
		}
	}

}
