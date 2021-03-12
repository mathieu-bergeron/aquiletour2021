package ca.ntro.services;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.json.JsonDeserialization;
import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.json.JsonSerialization;

public abstract class JsonService {
	
	private boolean prettyPrinting = false;
	private Map<String, Class<?>> serializableClasses = new HashMap<>();
	
	public void registerSerializableClass(Class<? extends JsonSerializable> _class) {
		serializableClasses.put(Ntro.introspector().getSimpleNameForClass(_class), _class);
	}

	public Class<?> serializableClass(String simpleName) {
		return serializableClasses.get(simpleName);
	}
	
	public void setPrettyPrinting(boolean prettyPrinting) {
		this.prettyPrinting = prettyPrinting;
	}

	protected boolean ifPrettyPrinting() {
		return prettyPrinting;
	}
	
	public String toString(Object javaValue) {

		return writeJson(JsonSerialization.toJsonValue(javaValue));
	}

	public <V extends Object> V fromString(Class<V> targetClass, String jsonString) {

		return JsonDeserialization.toJavaValue(targetClass, loadJson(jsonString));
	}

	protected abstract String writeJson(Object javaValue);
	protected abstract Object loadJson(String jsonString);
}
