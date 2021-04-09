package ca.ntro.core.json;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.core.introspection.Factory;
import ca.ntro.core.introspection.NtroClass;
import ca.ntro.core.introspection.NtroMethod;
import ca.ntro.core.system.log.Log;
import ca.ntro.services.Ntro;
import def.js.JSON;

public class JsonDeserialization {

	@SuppressWarnings("unchecked")
	public static <V extends Object> V toJavaValue(Class<V> targetClass, Object jsonValue) {
		
		return (V) toJavaValue(targetClass, jsonValue, "", new HashMap<>());
	}

	@SuppressWarnings("unchecked")
	private static Object toJavaValue(Class<?> targetClass, 
		                          	  Object jsonValue,
		                          	  String valuePath,
		                          	  Map<String, Object> localHeap) {
		
		Object javaValue = null;
		
		if(jsonValue instanceof List) {
			
			// XXX: no target class as we do not have type
			//      information on the items of the list
			javaValue = toJavaValueFromList((List<Object>) jsonValue, valuePath, localHeap);
			
		}else if(jsonValue instanceof Map) {

			javaValue = toJavaValueFromMap((Map<String, Object>) jsonValue, valuePath, localHeap);
			
		}else {

			javaValue = Ntro.introspector().castPrimitiveValue(targetClass, jsonValue);
		}
		
		return javaValue;
	}

	private static Object toJavaValueFromList(List<Object> jsonList,
			                                  String valuePath, 
			                                  Map<String, Object> localHeap) {

		List<Object> javaList = new ArrayList<>();

		localHeap.put(valuePath, javaList);
		
		for(int i = 0; i < jsonList.size(); i++) {
			
			Object jsonItem = jsonList.get(i);

			String itemValuePath = valuePath + "/" + i;

			Object javaValue = toJavaValue(Object.class, jsonItem, itemValuePath, localHeap);

			javaList.add(javaValue);
		}

		return javaList;
	}

	private static Object toJavaValueFromMap(Map<String, Object> jsonMap,
			                                 String valuePath,
			                                 Map<String, Object> localHeap) {
		
		Object result = null;
		
		if(isJsonSerializable(jsonMap)) {
			
			result = toJavaValueFromJsonSerializable(jsonMap, valuePath, localHeap);
			
		}else if(isObjectReference(jsonMap)) {

			result = toJavaValueFromObjectReference(jsonMap, localHeap);
			
		}else {
			
			result = toJavaValueFromSimpleMap(jsonMap, valuePath, localHeap);

		}

		return result;
	}
	

	private static boolean isJsonSerializable(Map<String, Object> jsonMap) {
		return jsonMap.containsKey(Constants.JSON_CLASS_KEY);
	}

	private static boolean isObjectReference(Map<String, Object> jsonMap) {
		return jsonMap.containsKey(Constants.JSON_REFERENCE_KEY);
	}

	@SuppressWarnings("unchecked")
	private static Object toJavaValueFromObjectReference(Map<String, Object> jsonMap, 
			                                            Map<String, Object> localHeap) {
		
		String referenceId = (String) jsonMap.get(Constants.JSON_REFERENCE_KEY);

		Object javaObject = localHeap.get(referenceId);
		
		return javaObject;
	}

	private static Object toJavaValueFromJsonSerializable(Map<String, Object> jsonMap, 
			                                             String valuePath, 
			                                             Map<String, Object> localHeap) {

		String targetClassSimpleName = (String) jsonMap.get(Constants.JSON_CLASS_KEY);
		Class<?> targetClass = Ntro.serializableClass(targetClassSimpleName);
		NtroClass ntroClass = Ntro.introspector().ntroClassFromJavaClass(targetClass);
		
		if(targetClass == null) {
			Log.fatalError("Unknown JsonSerializable: " + targetClassSimpleName);
		}
		
		Object javaObject = Ntro.factory().newInstance(targetClass);
		
		localHeap.put(valuePath, javaObject);

		List<NtroMethod> setters = ntroClass.userDefinedSetters();

		setters.sort(new Comparator<NtroMethod>() {
			@Override
			public int compare(NtroMethod o1, NtroMethod o2) {
				return o1.setterFieldName().compareTo(o2.setterFieldName());
			}
		});
		
		for(NtroMethod setter : setters) {
			
			String attributeName = setter.setterFieldName();
			Object jsonAttributeValue = jsonMap.get(attributeName);
			
			if(jsonAttributeValue != null) {

				Class<?> setterType = setter.getSetterType();
				
				String attributeValuePath = valuePath + "/" + attributeName;
				
				Object javaAttributeValue = toJavaValue(setterType, jsonAttributeValue, attributeValuePath, localHeap);
				
				try {

					List<Object> args = new ArrayList<>();
					args.add(javaAttributeValue);
					setter.invoke(javaObject, args);

				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

					Log.fatalError("Unable to invoke setter " + setter.name(), e);
				}
			}
		}

		return javaObject;

	}

	private static Object toJavaValueFromSimpleMap(Map<String, Object> jsonMap, 
			                                      String valuePath, 
			                                      Map<String, Object> localHeap) {
		
		Map<String, Object> javaMap = new HashMap<>();
		
		localHeap.put(valuePath, javaMap);
		
		List<String> keys = new ArrayList<>();
		keys.addAll(jsonMap.keySet());

		keys.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		
		for(String key : keys) {
			Object jsonValue = jsonMap.get(key);
			
			String newValuePath = valuePath + "/" + key;
			
			Object javaValue = toJavaValue(Object.class, jsonValue, newValuePath, localHeap);

			javaMap.put(key, javaValue);
		}

		return javaMap;
	}

}
