package ca.ntro.core.json;

import java.io.InputStream;
import java.util.Map;

import ca.ntro.core.Ntro;
import ca.ntro.core.introspection.Factory;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

public abstract class JsonParser {
	
	private static JsonParser instance;
	
	public static void initialize(JsonParser instance) {
		T.call(JsonParser.class);
		JsonParser.instance = instance;
	}
	
	protected abstract JsonObject jsonObjectImpl(); 

	public static JsonObject jsonObject() {
		T.call(JsonParser.class);
		
		JsonObject result = null;
		
		try {
			
			result = instance.jsonObjectImpl();
			
		}catch(NullPointerException e) {
			
			Log.fatalError(JsonParser.class.getSimpleName() + " must be initialized", e);
			
		}
		
		return result;
	}
	
	
	protected abstract JsonObject fromStringImpl(String jsonString);

	public static JsonObject fromString(String jsonString) {
		T.call(JsonParser.class);

		JsonObject result = null;
		
		try {
			
			result = instance.fromStringImpl(jsonString);
			
		}catch(NullPointerException e) {
			
			Log.fatalError(JsonParser.class.getSimpleName() + " must be initialized", e);
			
		}
		
		return result;
	}

	protected abstract JsonObject fromStreamImpl(InputStream jsonStream);

	public static JsonObject fromStream(InputStream jsonStream) {
		T.call(JsonParser.class);

		JsonObject result = null;
		
		try {
			
			result = instance.fromStreamImpl(jsonStream);
			
		}catch(NullPointerException e) {
			
			Log.fatalError(JsonParser.class.getSimpleName() + " must be initialized", e);
			
		}
		
		return result;
	}


	protected abstract String toStringImpl(JsonObject jsonObject);

	public static String toString(JsonObject jsonObject) {
		T.call(JsonParser.class);

		String result = null;
		
		try {
			
			result = instance.toStringImpl(jsonObject);
			
		}catch(NullPointerException e) {
			
			Log.fatalError(JsonParser.class.getSimpleName() + " must be initialized", e);
			
		}
		
		return result;
	}

	public static boolean isUserDefined(Object jsonValue) {
		T.call(JsonParser.class);
		
		return appointmentDefinedTypeName(jsonValue) != null;
	}
	
	@SuppressWarnings("unchecked")
	private static String appointmentDefinedTypeName(Object jsonValue) {
		T.call(JsonParser.class);
		
		String result = null;

		try {
			
			if(jsonValue != null) {
				
				Map<String, Object> jsonMap = (Map<String, Object>) jsonValue;
				
				result = (String) jsonMap.get(JsonObject.TYPE_KEY);
			}

		}catch(ClassCastException e) {}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public static Object buildUserDefined(Object jsonValue) {
		T.call(JsonParser.class);
		
		String typeName = appointmentDefinedTypeName(jsonValue);
		
		
		Class<?> typeClass = Ntro.introspector().getClassFromName(typeName);

		Object appointmentDefinedObject = Factory.newInstance(typeClass);
		
		try {
			
			((JsonObjectIO) appointmentDefinedObject).loadFromJsonObject(new JsonObject((Map<String, Object>) jsonValue));
			
		}catch(ClassCastException e) {}

		return appointmentDefinedObject;
	}


	
}
