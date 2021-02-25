package ca.ntro.core.json;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.ntro.core.Ntro;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

public abstract class JsonObjectIO implements Serializable {
	
	// FIXME: would be better package-private if possible
	public void loadFromJsonObject(JsonObject jsonObject) {
		T.call(this);

		for(String fieldName : jsonObject.keySet()) {
			
			Object jsonValue = jsonObject.get(fieldName);

			Method setter = Ntro.introspector().findSetter(this.getClass(), fieldName);
			
			if(setter != null) {

				try {

					Object setterValue = Ntro.introspector().buildValueForSetter(setter, jsonValue);
					
					setter.invoke(this, setterValue);

				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					
					Log.fatalError("[JsonObjectIO] Cannot invoke setter " + setter.getName(), e);
				}
			}
		}
	}
	
	public JsonObject toJsonObject() {
		T.call(this);
		
		Map<Object, String> serializedObjects = new HashMap<>();
		
		return toJsonObject(serializedObjects);
	}

	private JsonObject toJsonObject(Map<Object, String> serializedObjects) {
		T.call(this);
		
		JsonObject jsonObject = JsonParser.jsonObject();

		jsonObject.setTypeName(Ntro.introspector().getSimpleNameForClass(this.getClass()));
		
		for(Method getter : Ntro.introspector().userDefinedGetters(this)) {
			
			Object value = null;
			
			try {
				
				value = getter.invoke(this);
				
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

				Log.fatalError("Cannot invoke getter " + getter.getName(), e);

			}

			String fieldName = Ntro.introspector().fieldNameForGetter(getter);
			
			// TODO: go inside a Map or a List to look
			//       for appointment-defined values (JsonObjectIO)
			Object jsonValue = buildJsonValue(serializedObjects, value);
			
			jsonObject.put(fieldName, jsonValue);
		}
		
		return jsonObject;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object buildJsonValue(Map<Object, String> serializedObjects, Object value) {
		
		Object jsonValue = value;

		if(value instanceof JsonObjectIO) {

			jsonValue = ((JsonObjectIO) value).toJsonObject().toMap();

		}else if(value instanceof Map) {
			
			HashMap result = new HashMap();
			
			Map map = (Map) value;
			
			Set keySet = map.keySet();
			
			for(Object key : keySet) {
				
				Object mapValue = map.get(key);
				
				Object jsonMapValue = buildJsonValue(serializedObjects, mapValue);
				
				result.put(key, jsonMapValue);
			}
			
			jsonValue = result;
			
		}else if(value instanceof List) {
			
			List result = new ArrayList();
			
			List list = (List) value;
			
			for(Object item : list) {
				
				Object jsonItem = buildJsonValue(serializedObjects, item);
				
				result.add(jsonItem);
			}
			
			jsonValue = result;
		}
		
		return jsonValue;
	}

}
