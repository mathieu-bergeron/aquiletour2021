package ca.ntro.jdk.introspection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.introspection.MethodSignature;
import ca.ntro.core.introspection.NtroMethod;

public class NtroMethodJdk extends NtroMethod {

	public NtroMethodJdk(Method method) {
		super(method);
	}
	
	@Override
	public MethodSignature signature() {

		List<String> argumentTypes = new ArrayList<>();

		for(Type argumentType : method().getGenericParameterTypes()) {
			argumentTypes.add(simpleTypeName(argumentType.getTypeName()));
		}

		return new MethodSignature(method().getName(), 
				                   argumentTypes, 
				                   simpleTypeName(method().getGenericReturnType().getTypeName()), 
				                   modifiers(method()));
	}

	private List<String> modifiers(Method method){
		return modifiers(method.getModifiers());
	}

	private List<String> modifiers(int intModifiers){
		List<String> modifiers = new ArrayList<>();

		if(Modifier.isPublic(intModifiers)) {
			modifiers.add("public");
		}else if(Modifier.isProtected(intModifiers)) {
			modifiers.add("protected");
		}else if(Modifier.isPrivate(intModifiers)) {
			modifiers.add("private");
		}

		return modifiers;
	}

	private String simpleTypeName(String typeName) {

		String[] nameElements = typeName.split("\\.");
		typeName = nameElements[nameElements.length-1];

		return typeName;
	}

	@Override
	protected Class<?> getSetterTypeImpl() {
		Class<?> setterType = null;
		if(method().getParameterTypes().length > 0) {
			setterType = method().getParameterTypes()[0];
		}
		return setterType;
	}
}
