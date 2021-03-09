package ca.ntro.jsweet.services;

import ca.ntro.core.services.JsonService;
import def.js.JSON;

public class JsonServiceJSweet extends JsonService {

	@Override
	protected String writeJson(Object javaValue) {
		return JSON.stringify(javaValue);
	}

	@Override
	protected Object loadJson(String jsonString) {
		return JSON.parse(jsonString);
	}

}
