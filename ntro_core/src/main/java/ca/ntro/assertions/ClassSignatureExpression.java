package ca.ntro.assertions;

import ca.ntro.core.introspection.ClassSignature;

public class ClassSignatureExpression extends ValueExpression<ClassSignature> {
	
	private ClassSignature classSignature;
	
	public ClassSignatureExpression(ClassSignature classSignature) {
		this.classSignature = classSignature;
	}
	
	public AssertExpression doesExtend(Class<?> _class) {
		return new DoesExtendExpression(this, _class);
	}

	public AssertExpression doesImplement(Class<?> _interface) {
		return new DoesImplementExpression(this, _interface);
	}

	@Override
	public ClassSignature evaluate() {
		return classSignature;
	}

	@Override
	public String failMessage() {
		// TODO Auto-generated method stub
		return null;
	}
}
