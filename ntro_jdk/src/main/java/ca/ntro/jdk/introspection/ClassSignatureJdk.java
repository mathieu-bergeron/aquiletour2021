package ca.ntro.jdk.introspection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.ntro.core.Ntro;
import ca.ntro.core.introspection.ClassSignature;
import ca.ntro.core.introspection.MethodSignature;

public class ClassSignatureJdk extends ClassSignature {
	
	public ClassSignatureJdk(Class<?> _class) {
		super(_class);
	}

	@Override
	public String simpleName() {
		return _class().getSimpleName();
	}

	@Override
	public String name() {
		return _class().getName();
	}

	@Override
	public Set<ClassSignature> allInterfaces() {
		Set<ClassSignature> allInterfaces = new HashSet<>();
		
		allInterfaces.addAll(interfacesAndSuperInterfaces());
		
		allInterfaces.addAll(superClassInterfaces());
		
		return allInterfaces;
	}
	
	private Set<ClassSignature> interfacesAndSuperInterfaces(){
		Set<ClassSignature> interfacesAndSuperInterfaces = new HashSet<>();

		for(Class<?> _interface : _class().getInterfaces()) {
			interfacesAndSuperInterfaces.add(Ntro.introspector().classSignatureForClass(_interface));
			interfacesAndSuperInterfaces.addAll(new ClassSignatureJdk(_interface).interfacesAndSuperInterfaces());
		}
		
		return interfacesAndSuperInterfaces;
	}
	
	private Set<ClassSignature> superClassInterfaces(){
		Set<ClassSignature> superInterfaces = new HashSet<>();

		Class<?> superClass = _class().getSuperclass();

		if(superClass != null) {
			superInterfaces.addAll(Ntro.introspector().classSignatureForClass(superClass).allInterfaces());
		}

		return superInterfaces;
	}

	@Override
	public boolean isInterface() {
		return _class().isInterface();
	}

	@Override
	public Set<ClassSignature> allSuperclasses() {
		Set<ClassSignature> allSuperclasses = new HashSet<>();

		Class<?> superClass = _class().getSuperclass();

		if(superClass != null && !superClass.equals(Object.class)) {
			ClassSignature superClassSignature= Ntro.introspector().classSignatureForClass(superClass);
			allSuperclasses.add(superClassSignature);
			allSuperclasses.addAll(superClassSignature.allSuperclasses());
		}

		return allSuperclasses;
	}

	@Override
	protected List<MethodSignature> declaredMethods() {
		List<MethodSignature> declaredMethods = new ArrayList<>();
		
		for(Method declaredMethod : _class().getDeclaredMethods()) {
			declaredMethods.add(Ntro.introspector().methodSignature(declaredMethod));
		}
		
		return declaredMethods;
	}

}
