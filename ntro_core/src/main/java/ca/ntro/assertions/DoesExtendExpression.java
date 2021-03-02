package ca.ntro.assertions;

import ca.ntro.core.Ntro;
import ca.ntro.core.introspection.ClassSignature;

public class DoesExtendExpression extends SimpleAssertExpression {
	
	private ClassSignatureExpression classSignatureExpression;
	private Class<?> _class;

	public DoesExtendExpression(ClassSignatureExpression classSignatureExpression, Class<?> _class) {
		this.classSignatureExpression = classSignatureExpression;
		this._class = _class;
	}

	@Override
	public String failMessage() {
		ClassSignature classSignature = classSignatureExpression.evaluate();
		
		if(!classSignature.ifExtends(_class)) {

			return "doesExtend(" + Ntro.introspector().classSignatureForClass(_class).simpleName() + ")";
		}

		return null;
	}

}
