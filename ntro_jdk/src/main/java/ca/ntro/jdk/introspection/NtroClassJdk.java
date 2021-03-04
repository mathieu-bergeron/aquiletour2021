package ca.ntro.jdk.introspection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.ntro.core.Ntro;
import ca.ntro.core.introspection.NtroClass;
import ca.ntro.core.introspection.NtroMethod;
import ca.ntro.core.introspection.MethodSignature;

public class NtroClassJdk extends NtroClass {
	
	public NtroClassJdk(Class<?> _class) {
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
	public Set<NtroClass> allInterfaces() {
		Set<NtroClass> allInterfaces = new HashSet<>();
		
		allInterfaces.addAll(interfacesAndSuperInterfaces());
		
		allInterfaces.addAll(superClassInterfaces());
		
		return allInterfaces;
	}
	
	private Set<NtroClass> interfacesAndSuperInterfaces(){
		Set<NtroClass> interfacesAndSuperInterfaces = new HashSet<>();

		for(Class<?> _interface : _class().getInterfaces()) {
			interfacesAndSuperInterfaces.add(Ntro.introspector().ntroClassFromJavaClass(_interface));
			interfacesAndSuperInterfaces.addAll(new NtroClassJdk(_interface).interfacesAndSuperInterfaces());
		}
		
		return interfacesAndSuperInterfaces;
	}
	
	private Set<NtroClass> superClassInterfaces(){
		Set<NtroClass> superInterfaces = new HashSet<>();

		Class<?> superClass = _class().getSuperclass();

		if(superClass != null) {
			superInterfaces.addAll(Ntro.introspector().ntroClassFromJavaClass(superClass).allInterfaces());
		}

		return superInterfaces;
	}

	@Override
	public boolean isInterface() {
		return _class().isInterface();
	}

	@Override
	public Set<NtroClass> allSuperclasses() {
		Set<NtroClass> allSuperclasses = new HashSet<>();

		Class<?> superClass = _class().getSuperclass();

		if(superClass != null && !superClass.equals(Object.class)) {
			NtroClass superClassSignature= Ntro.introspector().ntroClassFromJavaClass(superClass);
			allSuperclasses.add(superClassSignature);
			allSuperclasses.addAll(superClassSignature.allSuperclasses());
		}

		return allSuperclasses;
	}

	@Override
	protected List<NtroMethod> declaredMethods(NtroClass rootClass) {
		List<NtroMethod> declaredMethods = new ArrayList<>();

		for(Method declaredMethod : _class().getDeclaredMethods()) {
			if(!rootClass.ifOverrides(declaredMethod)) {
				declaredMethods.add(Ntro.introspector().ntroMethod(declaredMethod));
			}
		}

		return declaredMethods;
	}
}
