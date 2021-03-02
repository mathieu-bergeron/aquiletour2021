package ca.ntro.assertions;

import ca.ntro.core.Ntro;

public class ObjectExpression extends SimpleAssertExpression {
	
	private Object object;

	public ObjectExpression(Object object) {
		this.object = object;
	}

	public DoesExtendExpression doesExtend(Class<?> _class) {
		ClassSignatureExpression classSignatureExpression = new ClassSignatureExpression(Ntro.introspector().getClassSignature(object));
		
		DoesExtendExpression result = new DoesExtendExpression(classSignatureExpression, _class);
		
		setChild(result);
		
		return result;
	}

	public DoesImplementExpression doesImplement(Class<?> _interface) {
		ClassSignatureExpression classSignatureExpression = new ClassSignatureExpression(Ntro.introspector().getClassSignature(object));

		DoesImplementExpression result = new DoesImplementExpression(classSignatureExpression, _interface);
		
		setChild(result);
		
		return result;
	}

	public IsInstanceOfExpression isInstanceOf(Class<?> classOrInterface) {
		ClassSignatureExpression classSignatureExpression = new ClassSignatureExpression(Ntro.introspector().getClassSignature(object));
		
		IsInstanceOfExpression result = new IsInstanceOfExpression(classSignatureExpression, classOrInterface);
		
		setChild(result);

		return result;
	}
}
