package ca.ntro.core.services;

import ca.ntro.core.json.JsonDeserialization;
import ca.ntro.core.json.JsonSerialization;

public abstract class JsonService {
	
	public String toString(Object javaValue) {

		return writeJson(JsonSerialization.toJsonValue(javaValue));
	}

	public <V extends Object> V fromString(Class<V> targetClass, String jsonString) {

		return JsonDeserialization.fromJsonValue(targetClass, loadJson(jsonString));
	}

	protected abstract String writeJson(Object javaValue);
	protected abstract Object loadJson(String jsonString);
}
