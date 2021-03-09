package ca.ntro.core.json;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.core.Ntro;
import ca.ntro.core.introspection.NtroClass;
import ca.ntro.core.introspection.NtroMethod;
import ca.ntro.core.services.NtroCollections;
import ca.ntro.core.system.log.Log;

public class JsonSerialization {

	public static Object toJsonValue(Object javaValue) {

		Map<Object, String> localHeap = new HashMap<>();
		
		String valuePath = "";

		return toJsonValue(javaValue, valuePath, localHeap);
	}
	

	private static Object toJsonValue(Object javaValue, String valuePath, Map<Object, String> localHeap) {
		
		Object jsonValue = null;
		
		if(javaValue == null) {
			
			jsonValue = null;
			
		} else if(NtroCollections.containsKeyExact(localHeap, javaValue)) {

			String referencePath = NtroCollections.getExactKey(localHeap, javaValue);

			jsonValue = jsonReferenceObject(referencePath);

		}else {
			
			jsonValue = toJsonValueWhenNotInLocalHeap(javaValue, valuePath, localHeap);
			
		}
		
		return jsonValue;
	}

	private static Object jsonReferenceObject(String valuePath) {

		Map<String, Object> jsonReferenceObject = new HashMap<>();

		jsonReferenceObject.put(Constants.JSON_REFERENCE_KEY, valuePath);

		return jsonReferenceObject;
	}

	@SuppressWarnings("unchecked")
	private static Object toJsonValueWhenNotInLocalHeap(Object javaValue, 
			                                            String valuePath,
			                                            Map<Object, String> localHeap) {

		Object jsonValue = null;

		NtroClass valueClass = Ntro.introspector().ntroClassFromObject(javaValue);

		if(valueClass.ifImplements(JsonSerializable.class)) {

			localHeap.put(javaValue, valuePath);
			
			jsonValue = toJsonValueFromSerializableObject((JsonSerializable) javaValue, valuePath, localHeap);
		
		} else if(javaValue instanceof List) {

			localHeap.put(javaValue, valuePath);
			
			jsonValue = toJsonValueFromList((List<Object>) javaValue, valuePath, localHeap);

		}else if(javaValue instanceof Map) {

			localHeap.put(javaValue, valuePath);

			jsonValue = toJsonValueFromMap((Map<String,Object>) javaValue, valuePath, localHeap);
			
		}else {

			jsonValue = javaValue; // primitive types
		}

		return jsonValue;
	}

	private static Object toJsonValueFromList(List<Object> javaList, String valuePath, Map<Object, String> localHeap) {

		List<Object> jsonList = new ArrayList<>();
		
		for(int i = 0; i < javaList.size(); i++) {
			
			Object javaItem = javaList.get(i);
			
			String newValuePath = valuePath + "/" + i;

			jsonList.add(toJsonValue(javaItem, newValuePath, localHeap));
		}

		return jsonList;
	}

	private static Object toJsonValueFromMap(Map<String, Object> javaMap, String valuePath, Map<Object, String> localHeap) {
		
		Map<String, Object> jsonMap = new HashMap<>();
		
		List<String> keys = new ArrayList<>();
		keys.addAll(javaMap.keySet());
		
		keys.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		
		for(String key : keys) {

			Object javaValue = javaMap.get(key);
			
			if(isSpecialKey(key)) {
				
				jsonMap.put(key, javaValue);
				
			}else {
				
				String newValuePath = valuePath + "/" + key;
				
				jsonMap.put(key, toJsonValue(javaValue, newValuePath, localHeap));
			}
		}

		return jsonMap;
	}
	
	static boolean isSpecialKey(String key) {
		return key.equals(Constants.JSON_CLASS_KEY) || key.equals(Constants.JSON_REFERENCE_KEY);
	}
	
	private static Object toJsonValueFromSerializableObject(JsonSerializable javaObject, String valuePath, Map<Object, String> localHeap) {

		Map<String, Object> javaMap = new HashMap<>();
		
		NtroClass javaClass = Ntro.introspector().ntroClassFromObject(javaObject);
		
		javaMap.put(Constants.JSON_CLASS_KEY, javaClass.simpleName());
		
		List<NtroMethod> getters = javaClass.userDefinedGetters();
		
		for(NtroMethod getter : getters) {
			
			String attributeName = getter.getterAttributeName();
			Object javaAttributeValue = null;

			try {

				javaAttributeValue = getter.invoke(javaObject);

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

				Log.fatalError("Unable to invoke getter " + getter.name(), e);
			}
			
			javaMap.put(attributeName, javaAttributeValue);
		}
		
		return toJsonValueFromMap(javaMap, valuePath, localHeap);
	}

}
