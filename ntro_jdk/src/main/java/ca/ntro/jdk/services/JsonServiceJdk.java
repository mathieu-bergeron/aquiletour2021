package ca.ntro.jdk.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ca.ntro.services.JsonService;

public class JsonServiceJdk extends JsonService {

	private static final Gson gsonPrettyPrint = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
	private static final Gson gson = new GsonBuilder().serializeNulls().create();

	@Override
	protected String writeJson(Object javaValue) {
		if(ifPrettyPrinting()) {
			return gsonPrettyPrint.toJson(javaValue);
		}else {
			return gson.toJson(javaValue);
		}
	}

	@Override
	protected Object loadJson(String jsonString) {
		return gson.fromJson(jsonString, Object.class);
	}

}
