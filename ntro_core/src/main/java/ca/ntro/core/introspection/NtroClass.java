package ca.ntro.core.introspection;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ca.ntro.services.Ntro;

public abstract class NtroClass {

	private Class<?> _class;
	
	public NtroClass(Class<?> _class) {
		this._class = _class;
	}
	
	protected Class<?> _class(){
		return _class;
	}
	
	public abstract boolean isInterface();

	public abstract String simpleName();
	public abstract String name();

	public boolean ifImplements(Class<?> interfaceClass) {
		return Ntro.collections().containsElementEquals(allInterfaces(), Ntro.introspector().ntroClassFromJavaClass(interfaceClass));
	}

	public abstract Set<NtroClass> allInterfaces();
	public abstract Set<NtroClass> allSuperclasses();

	public boolean ifExtends(Class<?> superClass) {
		return Ntro.collections().containsElementEquals(allSuperclasses(), Ntro.introspector().ntroClassFromJavaClass(superClass));
	}

	public boolean ifInstanceOf(Class<?> classOrInterface) {
		return ifImplements(classOrInterface) || ifExtends(classOrInterface);
	}

	@Override
	public int hashCode() {
		return _class.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other == this) return true;
		if(other instanceof NtroClass) {
			NtroClass otherNtroClass = (NtroClass) other;
			return _class.equals(otherNtroClass._class);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return simpleName();
	}

	public List<NtroMethod> userDefinedMethods() {
		List<NtroMethod> userDefinedMethods = new ArrayList<>();

		userDefinedMethods.addAll(declaredMethods(this));

		for(NtroClass superClass : allSuperclasses()) {
			for(NtroMethod superMethod : superClass.declaredMethods(this)) {
				if(!ifOverrides(superMethod)) {
					userDefinedMethods.add(superMethod);
				}
			}
		}

		return userDefinedMethods;
	}

	protected abstract List<NtroMethod> declaredMethods(NtroClass rootClass);

	public boolean ifOverrides(NtroMethod targetMethod) {
		boolean ifOverrides = false;

		// FIXME: in Jdk, we'll need to compare types as well
		for(NtroMethod declaredMethod : declaredMethods(this)) {
			if(declaredMethod.name().equals(targetMethod.name())) {
				ifOverrides = true;
				break;
			}
		}

		return ifOverrides;
	}

	public List<NtroMethod> userDefinedGetters() {
		List<NtroMethod> getters = new ArrayList<>();

		for(NtroMethod method : userDefinedMethods()) {
			if(method.isGetter()) {
				getters.add(method);
			}
		}

		return getters;
	}
	

	public List<NtroMethod> userDefinedSetters() {
		List<NtroMethod> setters = new ArrayList<>();

		for(NtroMethod method : userDefinedMethods()) {
			if(method.isSetter()) {
				setters.add(method);
			}
		}

		return setters;
	}

	public NtroMethod methodByName(String methodName) {
		NtroMethod method = null;

		for(NtroMethod candidate : userDefinedMethods()) {
			if(candidate.name().equals(methodName)) {
				method = candidate;
				break;
			}
		}
		
		return method;
	}

	public NtroMethod getterByFieldName(String fieldName) {
		NtroMethod method = null;

		for(NtroMethod candidate : userDefinedMethods()) {
			if(candidate.getterFieldName().equals(fieldName)) {
				method = candidate;
				break;
			}
		}

		return method;
	}

}
