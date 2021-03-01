package ca.ntro.assertions;

import ca.ntro.core.introspection.ClassSignature;
import ca.ntro.core.system.log.Log;

public class DoesExtendExpression extends AssertExpression {
	
	private ClassSignatureExpression classSignatureExpression;
	private Class<?> _class;

	public DoesExtendExpression(ClassSignatureExpression classSignatureExpression, Class<?> _class) {
		this.classSignatureExpression = classSignatureExpression;
		this._class = _class;
	}

	@Override
	void verify() {
		ClassSignature classSignature = classSignatureExpression.evaluate();

		if(!classSignature.ifExtends(_class)) {
			Log.fatalError("Assertion failed: " + classSignatureExpression.toString());
		}
	}

}
