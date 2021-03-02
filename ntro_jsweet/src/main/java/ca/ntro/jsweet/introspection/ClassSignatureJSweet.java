package ca.ntro.jsweet.introspection;

import ca.ntro.core.Ntro;
import ca.ntro.core.introspection.ClassSignature;

import static jsweet.util.Lang.object;
import static jsweet.util.Lang.typeof;

import java.util.HashSet;
import java.util.Set;

public class ClassSignatureJSweet extends ClassSignature {
	
	public ClassSignatureJSweet(Class<?> _class) {
		super(_class);
	}

	@Override
	public String simpleName() {
		String simpleName = null;

		if(isInterface()) {

			def.js.String interfaceName = new def.js.String(_class());
			simpleName = interfaceName.substring(interfaceName.lastIndexOf(".") + 1, interfaceName.length).toString();

		}else {

			simpleName = _class().getSimpleName();
		}
		
		return simpleName;
	}

	@Override
	public String name() {
		String name = null;

		if(isInterface()) {
			
			name = _class().toString();

		}else {
			
			name = _class().getName();
		}
		
		return name;
	}

	@Override
	public boolean isInterface() {
		return typeof(_class()).equals("string");
	}

	@Override
	public Set<ClassSignature> allInterfaces() {
		Set<ClassSignature> allInterfaces = new HashSet<>();
		
		def.js.Object jsClass = jsClass();
		
		def.js.String[] interfaces = jsClass.$get("__interfaces");

		for(def.js.String _interface : interfaces) {
			// JSWEET: an interface is represented as a String
			allInterfaces.add(jsClassSignature(_interface));
		}

		return allInterfaces;
	}
	
	private ClassSignatureJSweet jsClassSignature(Object any) {
		return new ClassSignatureJSweet((Class<?>) any);
	}

	@Override
	public Set<ClassSignature> allSuperclasses() {
		Set<ClassSignature> allSuperclasses = new HashSet<>();
		
		def.js.Object jsSuperClass = jsSuperClass();
		
		if(jsSuperClass != null) {

			ClassSignature superClass = jsClassSignature(jsSuperClass);

			allSuperclasses.add(superClass);
			allSuperclasses.addAll(superClass.allSuperclasses());
		}
		
		return allSuperclasses;
	}
	
	private def.js.Object jsClass(){
		return object(_class());
	}

	private def.js.Object jsSuperClass() {
		return jsClass().$get("__proto__");
	}

}
