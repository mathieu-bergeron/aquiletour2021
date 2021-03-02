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

package ca.ntro.core.introspection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

public abstract class Introspector {
	
	private Map<String, Class> serializableClasses = new HashMap<>();
	
	public void registerSerializableClass(Class _class) {
		serializableClasses.put(getSimpleNameForClass(_class), _class);
	}

	public Class getSerializableClass(String className) {
		return serializableClasses.get(className);
	}

	public abstract boolean isClass(Object object);

	public abstract ClassSignature classSignature(Object object);
	public abstract ClassSignature classSignatureForClass(Class<?> _class);

	public abstract boolean isMap(Object object);
	public abstract boolean isList(Object object);

	public abstract MethodSignature methodSignature(Method method);

	public Method findMethodBySignature(Class<?> currentClass, MethodSignature methodSignature) {
		T.call(Introspector.class);

		Method result = null;

		for(Method candidate : userDefinedMethodsFromClass(currentClass)) {

			MethodSignature candidateSignature = methodSignature(candidate);

			if(candidateSignature.equals(methodSignature)) {

				result = candidate;
				break;
			}
		}

		return result;
	}

	public abstract Object buildValueForSetter(Method setter, Object rawValue);


	public abstract Object buildValueForType(Class<?> type, Object rawValue);


	public Method findMethodByName(Class<?> _class, String methodName) {
		T.call(Introspector.class);

		Method result = null;

		for(Method method : userDefinedMethodsFromClass(_class)) {
			if(method.getName().equals(methodName)) {

				result = method;
				break;
			}
		}

		return result;
	}

	public abstract String getSimpleNameForClass(Class<?> clazz);

	public abstract String getFullNameForClass(Class<?> clazz);

	public Class<?> getClassFromName(String className){
		T.call(Introspector.class);

		Class<? extends Object> _class = null;
		
		if(serializableClasses.containsKey(className)) {

			_class = serializableClasses.get(className);

		}else {
			try {

				_class = Class.forName(className);

			} catch (ClassNotFoundException e) {

				Log.fatalError("Cannot find class " + className, e);

			}
		}

		return _class;
	}

	private String setterName(String fieldName) {
		T.call(Introspector.class);
		return "set" + capitalize(fieldName);
	}

	private String capitalize(String value) {
		T.call(Introspector.class);
		return value.substring(0, 1).toUpperCase() + value.substring(1);
	}


	private String unCapitalize(String value) {
		T.call(Introspector.class);
		return value.substring(0, 1).toLowerCase()+ value.substring(1);
	}

	public String fieldNameForGetter(Method method) {
		T.call(Introspector.class);

		String methodName = method.getName();

		// remove get
		String fieldName = methodName.substring(3);

		fieldName = unCapitalize(fieldName);

		return fieldName;
	}



	public Method findSetter(Class<?> _class, String fieldName) {
		T.call(Introspector.class);

		Method result = null;

		String setterName = setterName(fieldName);

		result = findMethodByName(_class, setterName);

		return result;
	}

	public boolean isAGetter(Method method) {
		T.call(Introspector.class);

		boolean isNotGetClass = !method.getName().equals("getClass");

		return isASetterOrSetter(method, "get") && isNotGetClass;
	}

	public boolean isASetter(Method method) {
		T.call(Introspector.class);

		return isASetterOrSetter(method, "set");
	}

	private boolean isASetterOrSetter(Method method, String prefix) {
		T.call(Introspector.class);

		String methodName = method.getName();

		boolean methodStartsWithPrefix = methodName.startsWith(prefix);

		boolean upperCaseAfterPrefix = false;

		if(methodName.length() > prefix.length()) {

			String letterAfterGet = methodName.substring(prefix.length(), prefix.length()+1);

			upperCaseAfterPrefix = letterAfterGet != null && letterAfterGet != "" && letterAfterGet.toUpperCase().equals(letterAfterGet);
		}


		return methodStartsWithPrefix && upperCaseAfterPrefix;
	}



	public List<Method> userDefinedMethodsFromObject(Object object) {
		T.call(Introspector.class);

		// FIXME: does not work in JSweet
		//MustNot.beTrue(object instanceof Class);
		return userDefinedMethodsFromClass(object.getClass());
	}

	public abstract List<Method> userDefinedMethodsFromClass(Class<?> _class);


	public abstract List<FieldSignature> userDefinedFieldsFromClass(Class<?> _class);

	public List<Method> appointmentDefinedSetters(Object object) {

		//MustNot.beTrue(object instanceof Class);

		List<Method> allSetters = new ArrayList<>();

		for(Method method : userDefinedMethodsFromObject(object)) {

			if(isASetter(method)) {

				allSetters.add(method);
			}
		}

		return allSetters;
	}

	public List<Method> userDefinedGetters(Object object) {
		T.call(Introspector.class);

		// FIXME: does not work in JSweet
		// MustNot.beTrue(object instanceof Class);

		List<Method> allGetters = new ArrayList<>();

		for(Method method : userDefinedMethodsFromObject(object)) {

			if(isAGetter(method)) {

				allGetters.add(method);
			}
		}

		return allGetters;
	}

}
