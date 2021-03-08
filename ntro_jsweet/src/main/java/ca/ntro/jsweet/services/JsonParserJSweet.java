package ca.ntro.jsweet.services;


import java.io.InputStream;
import java.util.Map;

import ca.ntro.core.json.JsonObject;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.system.trace.T;
import def.js.JSON;

public class JsonParserJSweet extends JsonParser { 

	@SuppressWarnings("unchecked")
	@Override
	protected JsonObject jsonObjectImpl() {
		T.call(this);

		// XXX: this works as  JSweet transpiles  Java Map to JS Object
		//      and casting is loose
		return new JsonObject((Map <String, Object>) new def.js.Object());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected JsonObject fromStringImpl(String jsonString) {
		T.call(this);
		
		def.js.Object jsObject = (def.js.Object) JSON.parse(jsonString);
		
		// XXX: this works as  JSweet transpiles  Java Map to JS Object
		//      and casting is loose
		return new JsonObject((Map<String, Object>) jsObject);
	}

	@Override
	protected String toStringImpl(JsonObject jsonObject) {
		T.call(this);
		
		return JSON.stringify(jsonObject.toMap());
	}

	@Override
	protected JsonObject fromStreamImpl(InputStream jsonStream) {
		return fromStringImpl(jsonStream.toString());
	}

}
