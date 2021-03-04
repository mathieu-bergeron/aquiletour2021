package ca.ntro.jsweet.introspection;

import java.lang.reflect.Method;

import ca.ntro.core.introspection.MethodSignature;
import ca.ntro.core.introspection.NtroMethod;

public class NtroMethodJSweet extends NtroMethod {

	public NtroMethodJSweet(Method method) {
		super(method);
	}

	@Override
	public MethodSignature signature() {
		throw new RuntimeException("TODO");
	}

}
