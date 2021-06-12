package ca.ntro.services;


import ca.ntro.core.json.JsonDeserialization;
import ca.ntro.core.json.JsonSerialization;

public abstract class JsonService {
	
	private boolean prettyPrinting = false;

	
	public void setPrettyPrinting(boolean prettyPrinting) {
		this.prettyPrinting = prettyPrinting;
	}

	protected boolean ifPrettyPrinting() {
		return prettyPrinting;
	}
	
	public String toString(Object javaValue) {

		return writeJson(JsonSerialization.toJsonValue(javaValue));
	}

	public String toString(Object javaValue, boolean prettyPrinting) {
		
		return writeJson(JsonSerialization.toJsonValue(javaValue), prettyPrinting);
	}

	public <V extends Object> V fromString(Class<V> targetClass, String jsonString) {

		return JsonDeserialization.toJavaValue(targetClass, loadJson(jsonString));
	}

	protected abstract String writeJson(Object javaValue);
	protected abstract String writeJson(Object javaValue, boolean prettyPrinting);
	protected abstract Object loadJson(String jsonString);
}
