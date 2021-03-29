package ca.ntro.core.json;

import ca.ntro.core.system.trace.T;
import ca.ntro.stores.DocumentPath;

public class JsonLoaderMemory extends JsonLoader {
	
	private String jsonString;
	private DocumentPath documentPath;
	
	public JsonLoaderMemory(DocumentPath documentPath, String jsonString) {
		super();
		T.call(this);
		
		this.jsonString = jsonString;
		this.documentPath = documentPath;
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getJsonString() {
		T.call(this);
		
		return jsonString;
	}
}
