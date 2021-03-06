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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.core.introspection.NtroClass;
import ca.ntro.core.introspection.NtroMethod;
import ca.ntro.core.introspection.ConstructorSignature;
import ca.ntro.core.introspection.FieldSignature;
import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.introspection.NtroClassJdk;
import ca.ntro.jdk.introspection.NtroMethodJdk;

public class IntrospectorJdk extends Introspector {

	@Override
	public Object buildValueForSetter(Method setter, Object rawValue) {
		T.call(this);

		Object result = null;

		Type setterType = setterType(setter);

		result = buildValue(setterType, rawValue);

		return result;
	}

	protected Object buildValue(Type type, Object rawValue) {
		T.call(this);

		Object result = null;

		if(JsonParser.isUserDefined(rawValue)) {

			result = JsonParser.buildUserDefined(rawValue);

		}else if(isAList(type)) {

			result = buildList(type, rawValue);

		}else if(isAMap(type)) {

			result = buildMap(type, rawValue);

		}else {

			result = buildSimpleValue(type, rawValue);
		}

		return result;
	}

	private Object buildSimpleValue(Type type, Object rawValue) {
		T.call(this);

		Object result = null;

		if(rawValue == null) {

			result = null;

		}else if(type.equals(Object.class)) {

			result = rawValue;

		}else if(type.equals(rawValue.getClass())) {

			result = rawValue;

		}else if(type.equals(String.class)) {

			result = String.valueOf(rawValue.toString());

		}else if(type.equals(Boolean.class) || type.equals(boolean.class)) {

			result = Boolean.valueOf(rawValue.toString());

		}else if(type.equals(Double.class) || type.equals(double.class)) {

			result = Double.valueOf(rawValue.toString());

		}else if(type.equals(Float.class) || type.equals(float.class)) {

			result = Float.valueOf(rawValue.toString());

		}else if(type.equals(Integer.class) || type.equals(int.class)) {

			Double resultDouble = Double.parseDouble(rawValue.toString());
			result = (int) Math.round(resultDouble);

		}else if(type.equals(Long.class) || type.equals(long.class)) {

			Double resultDouble = Double.parseDouble(rawValue.toString());
			result = (long) Math.round(resultDouble);

		}else if(type.equals(Character.class) || type.equals(char.class)) {

			Double resultDouble = Double.parseDouble(rawValue.toString());
			result = (char) Math.round(resultDouble);

		}else if(type instanceof Class){

			Class<?> _class = (Class<?>) type;

			try {

				result = _class.cast(rawValue);

			}catch(ClassCastException e) {

				Log.fatalError("Cannot cast rawValue into type " + type + " for rawValue " + rawValue + " of type " + rawValue.getClass());
			}


		}else {

			Log.fatalError("Unable to build simple value " + type + " for rawValue " + rawValue + " of type " + rawValue.getClass());
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> buildMap(Type targetType, Object value) {
		T.call(this);

		Map<String, Object> result = new HashMap<>();

		Type valueType = mapValueType(targetType);

		Map<String, Object> inputMap = (Map<String, Object>) value;

		for(String key : inputMap.keySet()) {

			Object inputValue = inputMap.get(key);

			Object outputValue = buildValue(valueType, inputValue);

			result.put(key, outputValue);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	private List<Object> buildList(Type targetType, Object value) {
		T.call(this);

		List<Object> result = new ArrayList<>();

		Type itemType = listItemType(targetType);

		List<Object> inputList = (List<Object>) value;

		for(Object inputItem : inputList) {

			Object outputValue = buildValue(itemType, inputItem);

			result.add(outputValue);
		}
		return result;
	}


	private Type listItemType(Type type) {
		T.call(this);
		return parameterType(type, 0);
	}

	private Type mapValueType(Type type) {
		T.call(this);
		return parameterType(type, 1);
	}

	private Type parameterType(Type type, int index) {
		T.call(this);

		Type result = null;

		try {

			result = ((ParameterizedType)type).getActualTypeArguments()[index];

		}catch(ClassCastException | IndexOutOfBoundsException | NullPointerException e) {}

		return result;
	}

	private boolean isAList(Type type) {
		T.call(this);

		boolean result = false;

		if(type == null) {

			result = false;

		}else if(type instanceof ParameterizedType) {

			Type rawType = ((ParameterizedType) type).getRawType();

			result = rawType.equals(List.class);

		}else if(type.equals(List.class)) {

			result = true;

		}else if(type instanceof Class<?>) {

			result = ifImplementsInterface((Class<?>) type, List.class);

		}

		return result;
	}

	private boolean isAMap(Type type) {
		T.call(this);


		boolean result = false;

		if(type instanceof ParameterizedType) {

			Type rawType = ((ParameterizedType) type).getRawType();

			result = rawType.equals(Map.class);

		}else if(type instanceof Class<?>) {

			result = ifImplementsInterface((Class<?>) type, Map.class);

		}

		return result;
	}

	@Override
	public String getSimpleNameForClass(Class<?> clazz) {
		return clazz.getSimpleName();
	}

	@Override
	public String getFullNameForClass(Class<?> clazz) {
		return clazz.getName();
	}

	private boolean ifImplementsInterface(Class<?> typeClass, Class<?> targetInterface) {
		T.call(this);

		boolean doesImplement = false;

		for(Class<?> _interface : typeClass.getInterfaces()) {
			if(_interface.equals(targetInterface)) {
				doesImplement = true;
			}
		}

		return doesImplement;
	}

	private Type setterType(Method setter) {
		T.call(this);

		Type result = null;

		Type[] parameterTypes = setter.getGenericParameterTypes();

		if(parameterTypes != null && parameterTypes.length > 0) {

			result = parameterTypes[0];
		}

		return result;
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
