// Copyright (C) (2020) (Mathieu Bergeron) (mathieu.bergeron@cmontmorency.qc.ca)
//
// This file is part of tutoriels4f5
//
// tutoriels4f5 is free software: you can redistribute it and/or modify
// it under the terms of the GNU Affero General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// tutoriels4f5 is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Affero General Public License for more details.
//
// You should have received a copy of the GNU Affero General Public License
// along with aquiletour.  If not, see <https://www.gnu.org/licenses/>

package ca.ntro.jsweet.introspection;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.core.introspection.FieldSignature;
import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.introspection.MethodSignature;
import ca.ntro.core.system.trace.T;
import def.js.Array;
import def.js.Function;
import def.js.JSON;

import static jsweet.util.Lang.object;
import static jsweet.util.Lang.typeof;

public class IntrospectorJSweet extends Introspector {

	@Override
	public Object buildValueForSetter(Method setter, Object jsonValue) {
		T.call(this);

		// XXX: setter does not contain type information in JSweet
		return buildValue(jsonValue);
	}

	private Object buildValue(Object jsonValue) {
		T.call(this);

		Object result = null;

		/*
		if(JsonParser.isUserDefined(jsonValue)) {

			result = JsonParser.buildUserDefined(jsonValue);

		}else*/ if(isAMap(jsonValue)) {

			result = buildMap(jsonValue);

		}else if(isAList(jsonValue)) {

			result = buildList(jsonValue);

		}else {

			result = buildSimpleValue(jsonValue);

		}

		return result;
	}

	private Object buildSimpleValue(Object jsonValue) {
		T.call(this);

		// XXX: nothing to do, JSweet maps simple values
		//      to their JS equivalent
		return jsonValue;
	}

	@SuppressWarnings("unchecked")
	private List<Object> buildList(Object jsonValue) {
		T.call(this);

		List<Object> inputList = (List<Object>) jsonValue;

		List<Object> outputList = new ArrayList<>();

		for(Object inputValue : inputList) {

			Object outputValue = buildValue(inputValue);

			outputList.add(outputValue);

		}
		return outputList;
	}

	@SuppressWarnings("unchecked")
	private Object buildMap(Object jsonValue) {
		T.call(this);

		Map<String, Object> inputMap = (Map<String, Object>) jsonValue;

		Map<String, Object> outputMap = new HashMap<>();

		for(String key : inputMap.keySet()) {

			Object inputValue = inputMap.get(key);

			Object outputValue = buildValue(inputValue);

			outputMap.put(key, outputValue);
		}

		return outputMap;
	}

	private boolean isAList(Object jsonValue) {
		T.call(this);

		return jsonValue != null && jsonValue.getClass().getName().equals("Array");
	}

	private boolean isAMap(Object jsonValue) {
		T.call(this);

		return jsonValue != null && jsonValue.getClass().getName().equals("Object");
	}

	@Override
	public Object buildValueForType(Class<?> superType, Object jsonValue) {
		T.call(this);

		return buildValue(jsonValue);
	}

	@Override
	public String getSimpleNameForClass(Class<?> clazz) {
		String ret;

		if (typeof(clazz).equals("string")) {
			def.js.String interfaceName = new def.js.String(clazz);

			ret = interfaceName.substring(interfaceName.lastIndexOf(".") + 1, interfaceName.length - 1).toString();
		} else {
			ret = clazz.getSimpleName();
		}

		return ret;
	}

	@Override
	public String getFullNameForClass(Class<?> clazz) {
		return clazz.getName();
	}

	@Override
	public List<Method> userDefinedMethodsFromClass(Class<?> javaClass) {
		T.call(this);

		List<Method> result = new ArrayList<>();

		def.js.Object jsClass = object(javaClass);

		result.addAll(methods(javaClass, jsClass));

		return result;
	}


	private List<Method> methods(Class<?> owner, def.js.Object jsClass) {
		T.call(this);

		List<Method> result = new ArrayList<>();

		result.addAll(declaredMethods(owner, jsClass));

		def.js.Object jsSuperClass = jsSuperClass(jsClass);

		if(jsSuperClass != null) {
			result.addAll(methods(owner, jsSuperClass));
		}

		return result;
	}

	private List<Method> declaredMethods(Class<?> owner,def.js.Object jsClass) {
		T.call(this);

		List<Method> result = new ArrayList<>();

		// XXX: does not return the same info as def.js.Object.getPrototypeOf()
		def.js.Object prototype = jsClass.$get("prototype");

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
					// FIXME: should the be the superclass
					//        where the method is defined??
					jsMethod.$set("owner", owner);

					// XXX: JSweet magic cast
					Object methodAsObject = object(jsMethod);

					Method javaMethod = (Method) methodAsObject;

					result.add(javaMethod);
				}
			}
		}

		return result;
	}

	private boolean isAJsSpecialFunction(String functionName) {
		T.call(this);

		boolean result = false;

		if(functionName.equals("constructor")) {
			result = true;
		}

		return result;
	}

	private def.js.Object jsSuperClass(def.js.Object jsObject) {
		T.call(this);

		def.js.Object superType = jsObject.$get("__proto__");

		return superType;
	}

	@Override
	public MethodSignature methodSignature(Method method) {
		throw new RuntimeException("TODO: IntrospectorJSweet.methodSignature");
	}

	@Override
	public List<FieldSignature> userDefinedFieldsFromClass(Class<?> javaClass) {
		throw new RuntimeException("TODO: IntrospectorJSweet.userDefinedFieldsFromClass");
	}

	@Override
	public boolean isClass(Object object) {
		return object.getClass().getSimpleName().equals("Function");
	}
}
