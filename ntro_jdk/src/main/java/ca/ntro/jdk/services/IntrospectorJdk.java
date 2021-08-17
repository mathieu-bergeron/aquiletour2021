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

package ca.ntro.jdk.services;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.ntro.core.introspection.NtroClass;
import ca.ntro.core.introspection.NtroMethod;
import ca.ntro.core.introspection.ConstructorSignature;
import ca.ntro.core.introspection.FieldSignature;
import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.introspection.NtroClassJdk;
import ca.ntro.jdk.introspection.NtroMethodJdk;

public class IntrospectorJdk extends Introspector {

	@Override
	public String getSimpleNameForClass(Class<?> _class) {
		return _class.getSimpleName();
	}

	@Override
	public String getFullNameForClass(Class<?> _class) {
		return _class.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object castPrimitiveValue(Class<?> targetClass, Object primitiveValue) {
		T.call(this);
		
		Object result = null;

		if(primitiveValue == null) {

			result = null;

		}else if(targetClass.equals(primitiveValue.getClass())) {

			result = primitiveValue;

		}else if(targetClass.equals(String.class)) {

			result = String.valueOf(primitiveValue.toString());

		}else if(targetClass.equals(Boolean.class) || targetClass.equals(boolean.class)) {

			result = Boolean.valueOf(primitiveValue.toString());

		}else if(targetClass.equals(Double.class) || targetClass.equals(double.class)) {

			result = Double.valueOf(primitiveValue.toString());

		}else if(targetClass.equals(Float.class) || targetClass.equals(float.class)) {

			result = Float.valueOf(primitiveValue.toString());

		}else if(targetClass.equals(Integer.class) || targetClass.equals(int.class)) {

			Double resultDouble = Double.parseDouble(primitiveValue.toString());
			Long resultLong = Math.round(resultDouble);
			result = Integer.parseInt(resultLong.toString());

		}else if(targetClass.equals(Long.class) || targetClass.equals(long.class)) {

			Double resultDouble = Double.parseDouble(primitiveValue.toString());
			result = Math.round(resultDouble);

		}else if(targetClass.equals(Character.class) || targetClass.equals(char.class)) {
			
			if(primitiveValue instanceof String) {

				String stringValue = (String) primitiveValue;
				if(stringValue.length() > 0) {
					result = targetClass.cast(((String)primitiveValue).charAt(0));
				}

			}else {

				Double resultDouble = Double.parseDouble(primitiveValue.toString());
				result = targetClass.cast((char) Math.round(resultDouble));
			}

		}else {
			
			result = targetClass.cast(primitiveValue);

		}

		return result;

	}

	private static String simpleTypeName(String typeName) {
		T.call(IntrospectorJdk.class);

		String[] nameElements = typeName.split("\\.");
		typeName = nameElements[nameElements.length-1];

		return typeName;
	}

	@SuppressWarnings("rawtypes")
	public static ConstructorSignature constructorSignature(Constructor constructor) {
		T.call(IntrospectorJdk.class);

		List<String> argumentTypes = new ArrayList<>();

		for(Type argumentType : constructor.getGenericParameterTypes()) {
			argumentTypes.add(simpleTypeName(argumentType.getTypeName()));
		}

		String name = simpleTypeName(constructor.getName());

		return new ConstructorSignature(name, argumentTypes, modifiers(constructor));
	}

	@Override
	public NtroMethod ntroMethod(Method method) {
		T.call(this);
		
		return new NtroMethodJdk(method);
	}


	public static FieldSignature fieldSignature(Field field) {
		T.call(IntrospectorJdk.class);

		return new FieldSignature(field.getName(), simpleTypeName(field.getGenericType().getTypeName()), modifiers(field));
	}

	@SuppressWarnings("rawtypes")
	private static List<String> modifiers(Constructor constructor){
		return modifiers(constructor.getModifiers());
	}

	@SuppressWarnings("unused")
	private static List<String> modifiers(Method method){
		T.call(IntrospectorJdk.class);

		return modifiers(method.getModifiers());
	}

	private static List<String> modifiers(Field field){
		T.call(IntrospectorJdk.class);

		return modifiers(field.getModifiers());
	}

	private static List<String> modifiers(int intModifiers){
		T.call(IntrospectorJdk.class);

		List<String> modifiers = new ArrayList<>();

		if(Modifier.isPublic(intModifiers)) {
			modifiers.add("public");
		}else if(Modifier.isProtected(intModifiers)) {
			modifiers.add("protected");
		}else if(Modifier.isPrivate(intModifiers)) {
			modifiers.add("private");
		}

		return modifiers;
	}

	@Override
	public List<Method> userDefinedMethodsFromClass(Class<?> _class) {
		T.call(this);

		List<Method> result = new ArrayList<>();

		for(Method m : _class.getDeclaredMethods()) {
			result.add(m);
		}

		Class<?> superClass = _class.getSuperclass();

		while(superClass != null && !superClass.equals(Object.class)) {

			for(Method m : superClass.getDeclaredMethods()) {
				if(!Modifier.isAbstract(m.getModifiers()) && !Modifier.isPrivate(m.getModifiers())) {
					result.add(m);
				}
			}

			superClass = superClass.getSuperclass();
		}

		return result;
	}

	@Override
	public List<FieldSignature> userDefinedFieldsFromClass(Class<?> _class) {
		T.call(this);

		List<FieldSignature> result = new ArrayList<>();

		for(Field f : _class.getDeclaredFields()) {
			result.add(fieldSignature(f));
		}

		Class<?> superClass = _class.getSuperclass();

		while(superClass != null && !superClass.equals(Object.class)) {

			for(Field f : superClass.getDeclaredFields()) {
				result.add(fieldSignature(f));
			}

			superClass = superClass.getSuperclass();
		}

		return result;
	}

	@Override
	public boolean isClass(Object object) {
		return object instanceof Class;
	}

	@Override
	public boolean isMap(Object object) {
		return object instanceof Map;
	}

	@Override
	public boolean isList(Object object) {
		return object instanceof List;
	}

	@Override
	public NtroClass ntroClassFromObject(Object object) {
		return ntroClassFromJavaClass(object.getClass());
	}

	@Override
	public NtroClass ntroClassFromJavaClass(Class<?> _class) {
		return new NtroClassJdk(_class);
	}
}
