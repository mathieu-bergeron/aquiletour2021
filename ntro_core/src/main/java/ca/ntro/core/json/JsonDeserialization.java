package ca.ntro.core.json;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.core.Ntro;

public class JsonDeserialization {

	public static <V extends Object> V toJavaValue(Class<V> targetClass, Object jsonValue) {
		return toJavaValue(targetClass, jsonValue, "", new HashMap<>());
	}

	@SuppressWarnings("unchecked")
	private static <V extends Object> V toJavaValue(Class<V> targetClass, 
			                                        Object jsonValue,
			                                        String valuePath,
			                                        Map<String, Object> localHeap) {
		
		V javaValue = null;

		
		if(jsonValue instanceof List) {
			
			javaValue = toJavaValueFromList(targetClass, (List<Object>) jsonValue, valuePath, localHeap);
			
		}else if(jsonValue instanceof Map) {

			javaValue = toJavaValueFromMap(targetClass, (Map<String, Object>) jsonValue, valuePath, localHeap);
			
		}else {
			
			javaValue = Ntro.introspector().castPrimitiveValue(targetClass, jsonValue);

		}
		
		return javaValue;
	}

	private static <V extends Object> V toJavaValueFromList(Class<V> targetClass, 
			                                                List<Object> jsonList,
			                                                String valuePath,
			                                                Map<String, Object> localHeap) {

		List<Object> javaList = new ArrayList<>();
		
		for(Object jsonItem : jsonList) {
			javaList.add(toJavaValue(Object.class, jsonItem, valuePath, localHeap));
		}

		return targetClass.cast(javaList);
	}

	private static <V extends Object> V toJavaValueFromMap(Class<V> targetClass, 
			                                               Map<String, Object> jsonMap,
			                                               String valuePath,
			                                               Map<String, Object> localHeap) {
		
		V result = null;
		
		if(isUserDefinedObject(jsonMap)) {
			
			
		}else if(isObjectReference(jsonMap)) {
			
			
		}else {
			
			result = toJavaValueFromSimpleMap(targetClass, jsonMap, valuePath, localHeap);
			
		}
		
		return result;
	}

	private static <V> V toJavaValueFromSimpleMap(Class<V> targetClass, Map<String, Object> jsonMap, String valuePath,
			Map<String, Object> localHeap) {
		Map<String, Object> javaMap = new HashMap<>();
		
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
			Object javaValue = toJavaValue(Object.class, jsonValue, valuePath, localHeap);
			javaMap.put(key, javaValue);
		}

		return targetClass.cast(javaMap);
	}

}
