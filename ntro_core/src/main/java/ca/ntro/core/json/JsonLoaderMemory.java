package ca.ntro.core.json;

import ca.ntro.core.models.stores.DocumentPath;
import ca.ntro.core.system.trace.T;

public class JsonLoaderMemory extends JsonLoader {
	
	private JsonObject jsonObject;
	private DocumentPath documentPath;
	
	public JsonLoaderMemory(DocumentPath documentPath, JsonObject jsonObject) {
		super();
		T.call(this);
		
		this.jsonObject = jsonObject;
		this.documentPath = documentPath;
	}


	@Override
	protected void initializeTask() {
		// TODO Auto-generated method stub
		
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
	public JsonObject getJsonObject() {
		T.call(this);
		
		return jsonObject;
	}

	@Override
	public DocumentPath getDocumentPath() {
		T.call(this);

		return documentPath;
	}


}
