package ca.ntro.core.introspection;

import java.util.Set;

import ca.ntro.core.Ntro;
import ca.ntro.core.services.NtroCollections;

public abstract class ClassSignature {

	private Class<?> _class;
	
	public ClassSignature(Class<?> _class) {
		this._class = _class;
	}
	
	protected Class<?> _class(){
		return _class;
	}
	
	public abstract boolean isInterface();

	public abstract String simpleName();
	public abstract String name();

	public boolean ifImplements(Class<?> interfaceClass) {
		return NtroCollections.ifSetContains(allInterfaces(), Ntro.introspector().classSignatureForClass(interfaceClass));
	}

	public abstract Set<ClassSignature> allInterfaces();
	public abstract Set<ClassSignature> allSuperclasses();

	public boolean ifExtends(Class<?> superClass) {
		return NtroCollections.ifSetContains(allSuperclasses(), Ntro.introspector().classSignatureForClass(superClass));
	}
	
	@Override
	public int hashCode() {
		return _class.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other == this) return true;
		if(other instanceof ClassSignature) {
			ClassSignature otherClassSignature = (ClassSignature) other;
			
			return _class.equals(otherClassSignature._class);
		}

		return false;
	}
	
	@Override
	public String toString() {
		return simpleName();
	}
}
