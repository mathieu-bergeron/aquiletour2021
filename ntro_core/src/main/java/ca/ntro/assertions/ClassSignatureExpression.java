package ca.ntro.assertions;

import ca.ntro.core.introspection.ClassSignature;

public class ClassSignatureExpression extends AssertExpression {
	
	private AssertExpression parentExpression;
	private ClassSignature classSignature;
	
	public ClassSignatureExpression(AssertExpression parentExpression, ClassSignature classSignature) {
		this.parentExpression = parentExpression;
		this.classSignature = classSignature;
	}
	
	public void doesExtend(Class<?> _class) {
		parentExpression.addSubExpression(new DoesExtendExpression(this, _class));
		parentExpression.verify();
	}

	public void doesImplement(Class<?> _interface) {
		parentExpression.addSubExpression(new DoesImplementExpression(this, _interface));
		parentExpression.verify();
	}

	public ClassSignature evaluate() {
		return classSignature;
	}

}
