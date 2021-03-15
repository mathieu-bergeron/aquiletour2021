package ca.ntro.jsweet.introspection;

import ca.ntro.core.introspection.NtroClass;
import ca.ntro.core.introspection.NtroMethod;
import def.js.Array;
import def.js.Function;

import static jsweet.util.Lang.object;
import static jsweet.util.Lang.typeof;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NtroClassJSweet extends NtroClass {
	
	public NtroClassJSweet(Class<?> _class) {
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
	public Set<NtroClass> allInterfaces() {
		Set<NtroClass> allInterfaces = new HashSet<>();
		
		def.js.Object jsClass = jsClass();
		
		def.js.String[] interfaces = jsClass.$get("__interfaces");

		if(interfaces != null) {
			for(def.js.String _interface : interfaces) {
				// JSWEET: an interface is represented as a String
				allInterfaces.add(jsClassSignature(_interface));
			}
		}

		/* JSWEET: superInterfaces already listed?
		for(NtroClass superClass : allSuperclasses()) {
			for(NtroClass superInterface : superClass.allInterfaces()) {
				allInterfaces.add(superInterface);
			}
		}*/

		return allInterfaces;
	}
	
	private NtroClassJSweet jsClassSignature(Object any) {
		return new NtroClassJSweet((Class<?>) any);
	}

	@Override
	public Set<NtroClass> allSuperclasses() {
		Set<NtroClass> allSuperclasses = new HashSet<>();
		
		def.js.Object jsSuperClass = jsSuperClass();
		
		if(jsSuperClass != null) {

			NtroClass superClass = jsClassSignature(jsSuperClass);

			allSuperclasses.add(superClass);
			allSuperclasses.addAll(superClass.allSuperclasses());
		}
		
		return allSuperclasses;
	}
	
	private def.js.Object jsClass(){
		return object(_class());
	}

	private def.js.Object jsSuperClass() {
		def.js.Object superClass = jsClass().$get("__proto__");

		if(!isUserDefinedClass(superClass)) {
			superClass = null;
		}

		return superClass;
	}

	private boolean isUserDefinedClass(def.js.Object jsClass) {
		return jsClass.$get("__class") != null;
	}

	@Override
	protected List<NtroMethod> declaredMethods(NtroClass rootClass) {
		List<NtroMethod> declaredMethods = new ArrayList<>();

		// XXX: does not return the same info as def.js.Object.getPrototypeOf()
		def.js.Object prototype = jsClass().$get("prototype");

		if(prototype != null) {
			Array<def.js.String> prototypeMethods = def.js.Object.getOwnPropertyNames(prototype);

			for(def.js.String method : prototypeMethods) {

				String methodName = method.toString();

				if(!isAJsSpecialFunction(methodName)) {

					/* In JSweet a Method is a def.js.Object with
					 *
					 * fn: the actual function
					 * name: Method.getName()
					 * owner: the Class<?> where the method lives
					 *
					 */

					def.js.Object jsMethod = new def.js.Object();

					Function fn = prototype.$get(methodName);

					jsMethod.$set("fn", fn);
					jsMethod.$set("name", methodName);
					// XXX: owner is the class on which the method
					//      would be invoked
					jsMethod.$set("owner", ((NtroClassJSweet)rootClass)._class());

					// JSWEET: magic cast
					Object methodAsObject = object(jsMethod);

					Method javaMethod = (Method) methodAsObject;

					declaredMethods.add(new NtroMethodJSweet(javaMethod));
				}
			}
		}
		
		return declaredMethods;
	}

	private boolean isAJsSpecialFunction(String functionName) {
		boolean result = false;

		if(functionName.equals("constructor")) {
			result = true;
		}

		return result;
	}

}
