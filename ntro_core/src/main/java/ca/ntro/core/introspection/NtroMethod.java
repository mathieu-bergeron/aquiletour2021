package ca.ntro.core.introspection;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class NtroMethod {
	
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

	public abstract MethodSignature signature();
}
