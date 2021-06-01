package ca.ntro.core.introspection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import ca.ntro.core.system.log.Log;

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

	public String getterFieldName() {
		return fieldName(GETTER_PREFIX);
	}
	
	private String fieldName(String prefix) {
		String capitalizedFieldName = name().substring(prefix.length());
		String firstLetter = capitalizedFieldName.substring(0, 1).toLowerCase();
		String remainder = capitalizedFieldName.substring(1);

		return firstLetter + remainder;
	}

	public boolean isSetter() {
		return name().startsWith(SETTER_PREFIX) && name().length() > SETTER_PREFIX.length();
	}

	public String setterFieldName() {
		return fieldName(SETTER_PREFIX);
	}


	public abstract MethodSignature signature();

	public Object invoke(Object targetObject) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return method.invoke(targetObject);
	}

	public Object invoke(Object targetObject, List<Object> args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// JSWEET: varags issue
		if(args.size() == 0) {
			return method.invoke(targetObject);
		}else if(args.size() == 1) {
			return method.invoke(targetObject, args.get(0));
		}else if(args.size() == 2) {
			return method.invoke(targetObject, args.get(0), args.get(1));
		}else if(args.size() == 3) {
			return method.invoke(targetObject, args.get(0), args.get(1), args.get(2));
		}else if(args.size() == 4) {
			return method.invoke(targetObject, args.get(0), args.get(1), args.get(2), args.get(3));
		}else if(args.size() == 5) {
			return method.invoke(targetObject, args.get(0), args.get(1), args.get(2), args.get(3), args.get(4));
		}else if(args.size() == 6) {
			return method.invoke(targetObject, args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5));
		}else if(args.size() == 7) {
			return method.invoke(targetObject, args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5), args.get(6));
		}else if(args.size() == 8) {
			return method.invoke(targetObject, args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5), args.get(6), args.get(7));
		}else if(args.size() == 9) {
			return method.invoke(targetObject, args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5), args.get(6), args.get(7), args.get(8));
		}else if(args.size() == 10) {
			return method.invoke(targetObject, args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5), args.get(6), args.get(7), args.get(8), args.get(9));
		}else {
			Log.fatalError("NtroMethod.invoke supports only up to 10 arguments");
			return null;
		}
	}

	protected abstract Class<?> getSetterTypeImpl();

	public Class<?> getSetterType() {
		if(!isSetter()) {
			throw new IllegalArgumentException("method is not a setter");
		}else {
			return getSetterTypeImpl();
		}
	}
}
