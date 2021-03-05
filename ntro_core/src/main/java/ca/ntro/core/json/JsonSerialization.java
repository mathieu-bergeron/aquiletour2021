package ca.ntro.core.json;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.core.Ntro;
import ca.ntro.core.introspection.NtroClass;
import ca.ntro.core.introspection.NtroMethod;
import ca.ntro.core.system.log.Log;

public class JsonSerialization {

	public static Object toJsonValue(Object javaValue) {

		Map<Object, String> localHeap = new HashMap<>();
		
		String valuePath = "";

		return toJsonValue(javaValue, valuePath, localHeap);
	}
	
	// FIXME: push this into NtroCollections.containsXXX
	private static boolean containsKeyByReference(Map<Object, String> localHeap, Object key) {
		boolean containsKey = false;
		
		for(Object candidateKey : localHeap.keySet()) {
			if(candidateKey == key) {
				containsKey = true;
				break;
			}
		}
		
		return containsKey;
	}

	private static String getValueByReference(Map<Object, String> localHeap, Object key) {
		String value = null;
		
		for(Map.Entry<Object, String> entry : localHeap.entrySet()) {
			if(entry.getKey() == key) {
				value = entry.getValue();
				break;
			}
		}

		return value;
	}

	private static Object toJsonValue(Object javaValue, String valuePath, Map<Object, String> localHeap) {
		
		Object jsonValue = null;

		if(containsKeyByReference(localHeap, javaValue)) {

			String referencePath = getValueByReference(localHeap, javaValue);

			jsonValue = jsonReferenceObject(referencePath);

		}else {
			
			localHeap.put(javaValue, valuePath);

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
			
			jsonValue = toJsonValueFromSerializableObject((JsonSerializable) javaValue, valuePath, localHeap);
		
		} else if(javaValue instanceof List) {
			
			jsonValue = toJsonValueFromList((List<Object>) javaValue, valuePath, localHeap);

		}else if(javaValue instanceof Map) {

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
		
		for(Map.Entry<String, Object> entry : javaMap.entrySet()) {
			
			String newValuePath = valuePath + "/" + entry.getKey();
			
			jsonMap.put(entry.getKey(), toJsonValue(entry.getValue(), newValuePath, localHeap));
		}

		return jsonMap;
	}

	private static Object toJsonValueFromSerializableObject(JsonSerializable javaObject, String valuePath, Map<Object, String> localHeap) {

		Map<String, Object> jsonObject = new HashMap<>();
		
		NtroClass javaClass = Ntro.introspector().ntroClassFromObject(javaObject);
		
		jsonObject.put(Constants.JSON_CLASS_KEY, javaClass.simpleName());
		
		List<NtroMethod> getters = javaClass.userDefinedGetters();
		
		for(NtroMethod getter : getters) {
			
			String attributeName = getter.getterAttributeName();
			Object javaAttributeValue = null;

			try {

				javaAttributeValue = getter.invoke(javaObject);

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

				Log.fatalError("Unable to invoke getter " + getter.name(), e);
			}
			
			String newValuePath = valuePath + "/" + attributeName;

			Object jsonAttributeValue = toJsonValue(javaAttributeValue, newValuePath, localHeap);
			
			jsonObject.put(attributeName, jsonAttributeValue);
		}
		
		return jsonObject;
	}

}
