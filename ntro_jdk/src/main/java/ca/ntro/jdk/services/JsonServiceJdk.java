package ca.ntro.jdk.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ca.ntro.services.JsonService;
import ca.ntro.services.Ntro;

public class JsonServiceJdk extends JsonService {

	private static final Gson gsonPrettyPrint = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
	private static final Gson gson = new GsonBuilder().create();

	@Override
	protected String writeJson(Object javaValue) {
		if(Ntro.config().isProd()) {
			return gson.toJson(javaValue);
		}else {
			return gsonPrettyPrint.toJson(javaValue);
		}
	}

	@Override
	protected Object loadJson(String jsonString) {
		return gson.fromJson(jsonString, Object.class);
	}

}
