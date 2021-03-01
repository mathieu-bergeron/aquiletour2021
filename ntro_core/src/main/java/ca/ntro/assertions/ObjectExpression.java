package ca.ntro.assertions;

import ca.ntro.core.Ntro;

public class ObjectExpression extends AssertExpression {
	
	private AssertExpression parentExpression;
	private Object object;

	public ObjectExpression(AssertExpression parentExpression, Object object) {
		this.parentExpression = parentExpression;
		this.object = object;
	}

	public void doesExtend(Class<?> _class) {
		ClassSignatureExpression classSignatureExpression = new ClassSignatureExpression(parentExpression, Ntro.introspector().getClassSignature(object));
		parentExpression.addSubExpression(new DoesExtendExpression(classSignatureExpression, _class));
		parentExpression.verify();
	}

	public void doesImplement(Class<?> _interface) {
		ClassSignatureExpression classSignatureExpression = new ClassSignatureExpression(parentExpression, Ntro.introspector().getClassSignature(object));
		parentExpression.addSubExpression(new DoesImplementExpression(classSignatureExpression, _interface));
		parentExpression.verify();
	}

}
