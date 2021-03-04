package ca.ntro.core.introspection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ca.ntro.core.json.JsonSerializable;

public abstract class NtroMethod {
	
	private static final String SETTER_PREFIX = "set";
	private static final String GETTER_PREFIX = "get";
	
	private Method method;
	
	public NtroMethod(Method method) {
		this.method = method;
	}
	
	protected Method method() {
		return method;
	}

	public boolean hasSignature(MethodSignature methodSignature) {
		throw new RuntimeException("TODO");
	}

	public String name() {
		return method.getName();
	}

	public boolean isGetter() {
		return name().startsWith(GETTER_PREFIX) && name().length() > GETTER_PREFIX.length();
	}

	public String getterAttributeName() {
		return attributeName(GETTER_PREFIX);
	}
	
	private String attributeName(String prefix) {
		String capitalizedAttributeName = name().substring(prefix.length());
		String firstLetter = capitalizedAttributeName.substring(0, 1).toLowerCase();
		String remainder = capitalizedAttributeName.substring(1);

		return firstLetter + remainder;
	}

	public boolean isSetter() {
		return name().startsWith(SETTER_PREFIX) && name().length() > SETTER_PREFIX.length();
	}

	public String setterAttributeName() {
		return attributeName(SETTER_PREFIX);
	}


	public abstract MethodSignature signature();

	public Object invoke(Object targetObject, Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return method.invoke(targetObject, args);
	}
}
