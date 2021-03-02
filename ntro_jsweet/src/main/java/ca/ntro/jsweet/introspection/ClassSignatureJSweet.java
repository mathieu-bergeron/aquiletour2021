package ca.ntro.jsweet.introspection;

import ca.ntro.core.Ntro;
import ca.ntro.core.introspection.ClassSignature;
import ca.ntro.core.introspection.MethodSignature;
import def.js.Array;
import def.js.Function;

import static jsweet.util.Lang.object;
import static jsweet.util.Lang.typeof;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
	protected List<MethodSignature> declaredMethods() {
		List<MethodSignature> declaredMethods = new ArrayList<>();

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

					// FIXME: this is not correct
					//        the owner should be the
					//        class where the method 
					//        will be executed
					jsMethod.$set("owner", _class());

					// XXX: JSweet magic cast
					Object methodAsObject = object(jsMethod);

					Method javaMethod = (Method) methodAsObject;

					declaredMethods.add(new MethodSignature(methodName));
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
