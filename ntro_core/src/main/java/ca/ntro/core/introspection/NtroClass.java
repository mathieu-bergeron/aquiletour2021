package ca.ntro.core.introspection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import ca.ntro.core.Ntro;
import ca.ntro.core.services.NtroCollections;

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
		return NtroCollections.ifSetContains(allInterfaces(), Ntro.introspector().ntroClassFromJavaClass(interfaceClass));
	}

	public abstract Set<NtroClass> allInterfaces();
	public abstract Set<NtroClass> allSuperclasses();

	public boolean ifExtends(Class<?> superClass) {
		return NtroCollections.ifSetContains(allSuperclasses(), Ntro.introspector().ntroClassFromJavaClass(superClass));
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
			NtroClass otherClassSignature = (NtroClass) other;
			
			return _class.equals(otherClassSignature._class);
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
			// FIXME: we need to inject "owner" in JSweet
			// FIXME: we need to remove overriden super methods
			userDefinedMethods.addAll(superClass.declaredMethods(this));
		}
		
		userDefinedMethods.sort(new Comparator<NtroMethod>() {
			@Override
			public int compare(NtroMethod m1, NtroMethod m2) {
				return m1.name().compareTo(m2.name());
			}});

		return userDefinedMethods;
	}

	protected abstract List<NtroMethod> declaredMethods(NtroClass rootClass);

}
