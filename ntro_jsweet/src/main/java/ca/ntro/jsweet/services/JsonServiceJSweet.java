package ca.ntro.jsweet.services;

import ca.ntro.core.system.trace.T;
import ca.ntro.services.JsonService;
import def.js.JSON;

public class JsonServiceJSweet extends JsonService {

	@Override
	protected String writeJson(Object javaValue) {
		T.call(this);

		return JSON.stringify(javaValue);
	}

	@Override
	protected String writeJson(Object javaValue, boolean prettyPrinting) {
		T.call(this);

		return JSON.stringify(javaValue);
	}

	@Override
	protected Object loadJson(String jsonString) {
		T.call(this);

		return JSON.parse(jsonString);
	}


}
