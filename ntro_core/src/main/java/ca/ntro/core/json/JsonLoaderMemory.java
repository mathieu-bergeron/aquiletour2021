package ca.ntro.core.json;

import ca.ntro.core.system.trace.T;

public class JsonLoaderMemory extends JsonLoader {
	
	private JsonObject jsonObject;
	
	public JsonLoaderMemory(JsonObject jsonObject) {
		super();
		T.call(this);
		
		this.jsonObject = jsonObject;
	}

	@Override
	public JsonObject getJsonObject() {
		T.call(this);
		
		return jsonObject;
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

}
