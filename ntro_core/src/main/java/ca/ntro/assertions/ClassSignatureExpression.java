package ca.ntro.assertions;

import ca.ntro.core.introspection.NtroClass;

public class ClassSignatureExpression extends ValueExpression<NtroClass> {
	
	private NtroClass classSignature;
	
	public ClassSignatureExpression(NtroClass classSignature) {
		this.classSignature = classSignature;
	}
	
	public AssertExpression doesExtend(Class<?> _class) {
		return new DoesExtendExpression(this, _class);
	}

	public AssertExpression doesImplement(Class<?> _interface) {
		return new DoesImplementExpression(this, _interface);
	}

	@Override
	public NtroClass evaluate() {
		return classSignature;
	}

	@Override
	public String failMessage() {
		// TODO Auto-generated method stub
		return null;
	}
}
