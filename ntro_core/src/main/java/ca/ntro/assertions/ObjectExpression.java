package ca.ntro.assertions;

import ca.ntro.services.Ntro;

public class ObjectExpression extends SimpleAssertExpression {
	
	private Object object;

	public ObjectExpression(Object object) {
		this.object = object;
	}

	public DoesExtendExpression doesExtend(Class<?> _class) {
		NtroClassExpression classSignatureExpression = new NtroClassExpression(Ntro.introspector().ntroClassFromObject(object));
		
		DoesExtendExpression result = new DoesExtendExpression(classSignatureExpression, _class);
		
		setChild(result);
		
		return result;
	}

	public DoesImplementExpression doesImplement(Class<?> _interface) {
		NtroClassExpression classSignatureExpression = new NtroClassExpression(Ntro.introspector().ntroClassFromObject(object));

		DoesImplementExpression result = new DoesImplementExpression(classSignatureExpression, _interface);
		
		setChild(result);
		
		return result;
	}

	public IsInstanceOfExpression isInstanceOf(Class<?> classOrInterface) {
		NtroClassExpression classSignatureExpression = new NtroClassExpression(Ntro.introspector().ntroClassFromObject(object));
		
		IsInstanceOfExpression result = new IsInstanceOfExpression(classSignatureExpression, classOrInterface);
		
		setChild(result);

		return result;
	}
}
