package ca.ntro.jdk.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.JsonService;
import ca.ntro.services.Ntro;

public class JsonServiceJdk extends JsonService {

	private static final Gson gsonPrettyPrint = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
	private static final Gson gson = new GsonBuilder().create();

	@Override
	protected String writeJson(Object javaValue) {
		T.call(this);

		return writeJson(javaValue, (ifPrettyPrinting() && !Ntro.config().isProd()));
	}

	@Override
	protected String writeJson(Object javaValue, boolean prettyPrinting) {
		T.call(this);

		String result = null;

		if(prettyPrinting) {

			result = gsonPrettyPrint.toJson(javaValue);

		}else {

			result = gson.toJson(javaValue);
		}
		
		return result;
	}

	@Override
	protected Object loadJson(String jsonString) {
		
		Object result = null;
		
		try {
			
			result = gson.fromJson(jsonString, Object.class);
			
		}catch(Exception e) {
			
			Log.error("[loadJson] error for jsonString " + jsonString);
			
		}
		
		
		return result;
	}


}
