package ca.ntro.core.json;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public abstract class JsonObjectIO implements Serializable {
	
	// FIXME: would be better package-private if possible
	public void loadFromJsonObject(JsonObject jsonObject) {
		T.call(this);
		
		Map<String, Object> deserializedObjects = new HashMap<>();
		
		deserializedObjects.put("", this);
		
		loadFromJsonObject(deserializedObjects, "", jsonObject);
	}

	public void loadFromJsonObject(Map<String, Object> deserializedObjects, String valuePath, JsonObject jsonObject) {
		T.call(this);

		for(String fieldName : jsonObject.keySet()) {
			
			Object jsonValue = jsonObject.get(fieldName);

			Method setter = Ntro.introspector().findSetter(this.getClass(), fieldName);
			
			if(setter != null) {

				try {

					Object setterValue;

					if(isObjectReference(jsonValue)) {

						String objectReference = getObjectReference(jsonValue);
						
						setterValue = deserializedObjects.get(objectReference);
						
						throw new RuntimeException("Cycle references not yet supported in JSON deserialization");
						
						
					}else {
						
						setterValue = Ntro.introspector().buildValueForSetter(setter, jsonValue);
						
					}
					
					setter.invoke(this, setterValue);

				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					
					Log.fatalError("[JsonObjectIO] Cannot invoke setter " + setter.getName(), e);
				}
			}
		}
	}
	
	private boolean isObjectReference(Object jsonValue) {
		return getObjectReference(jsonValue) != null;
	}

	private String getObjectReference(Object jsonValue) {
		String objectReference = null;
		
		if(Ntro.introspector().isMap(jsonValue)) {
			
			Map<String, Object> map = (Map<String, Object>) jsonValue;
			
			objectReference = (String) map.get("_I");
		}
		
		return objectReference;
	}
	
	
	
	public JsonObject toJsonObject() {
		T.call(this);
		
		Map<Object, String> serializedObjects = new HashMap<>();
		
		serializedObjects.put(this, "");
		
		return toJsonObject(serializedObjects, "");
	}

	private JsonObject toJsonObject(Map<Object, String> serializedObjects, String valuePath) {
		T.call(this);
		
		JsonObject jsonObject = JsonParser.jsonObject();

		//jsonObject.setTypeName(Ntro.introspector().getSimpleNameForClass(this.getClass()));
		jsonObject.setTypeName(Ntro.introspector().getFullNameForClass(this.getClass()));
		
		for(Method getter : Ntro.introspector().userDefinedGetters(this)) {
			
			Object value = null;
			
			try {
				
				value = getter.invoke(this);
				
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

				Log.fatalError("Cannot invoke getter " + getter.getName(), e);

			}

			String fieldName = Ntro.introspector().fieldNameForGetter(getter);
			
			String newValuePath = valuePath + "/"  + fieldName;

			Object jsonValue = buildJsonValue(serializedObjects, newValuePath, value);
			

			jsonObject.put(fieldName, jsonValue);
		}
		
		return jsonObject;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object buildJsonValue(Map<Object, String> serializedObjects, String valuePath, Object value) {
		
		Object jsonValue = value;

		if(value instanceof JsonObjectIO) {
			
			if(serializedObjects.containsKey(value)) {
				
				Map<String, Object> reference = new HashMap<>();
				reference.put("_I", serializedObjects.get(value));
				jsonValue = reference;

			}else {

				serializedObjects.put(value, valuePath);
				jsonValue = ((JsonObjectIO) value).toJsonObject(serializedObjects, valuePath).toMap();
			}

		}else if(Ntro.introspector().isList(value)) {
			List result = new ArrayList();
			
			List list = (List) value;
			
			for(int index = 0; index < list.size(); index++) {
				
				Object item = list.get(index);
				
				Object jsonItem = buildJsonValue(serializedObjects, valuePath + "/" + index, item);
				
				result.add(jsonItem);
			}
			
			jsonValue = result;

		}else if(Ntro.introspector().isMap(value) && ((Map)value).size() > 0) {

			Map<String, Object> map = null;
			
			try {

				map = (Map<String, Object>) value;

			}catch(ClassCastException e) {
				
				// JSWEET: will not throw in JS
				Log.fatalError("Only Map<String, ?> are supported for serialization", e);
			}
			
			Map<String, Object> result = new HashMap<>();
			
			for(String key : map.keySet()) {
				
				Object mapValue = map.get(key);
				
				Object jsonMapValue = buildJsonValue(serializedObjects, valuePath + "/" + key,  mapValue);
				
				result.put(key, jsonMapValue);
			}
			
			jsonValue = result;
		}
		
		return jsonValue;
	}

}
