package ca.ntro.jdk.services;

import ca.ntro.core.services.JsonService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonServiceJdk extends JsonService {

	//private static final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
	private static final Gson gson = new GsonBuilder().serializeNulls().create();

	@Override
	protected String writeJson(Object javaValue) {
		return gson.toJson(javaValue);
	}

	@Override
	protected Object loadJson(String jsonString) {
		return gson.fromJson(jsonString, Object.class);
	}

}
