package ca.ntro.assertions;

import ca.ntro.core.introspection.NtroClass;

public class NtroClassExpression extends ValueExpression<NtroClass> {
	
	private NtroClass ntroClass;
	
	public NtroClassExpression(NtroClass classSignature) {
		this.ntroClass = classSignature;
	}
	
	public AssertExpression doesExtend(Class<?> _class) {
		return new DoesExtendExpression(this, _class);
	}

	public AssertExpression doesImplement(Class<?> _interface) {
		return new DoesImplementExpression(this, _interface);
	}

	@Override
	public NtroClass evaluate() {
		return ntroClass;
	}

	@Override
	public String failMessage() {
		return null;
	}
}
