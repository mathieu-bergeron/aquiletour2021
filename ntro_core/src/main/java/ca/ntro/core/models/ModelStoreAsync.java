package ca.ntro.core.models;

import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.json.JsonObject;
import ca.ntro.core.models.stores.DocumentPath;
import ca.ntro.core.system.trace.T;


public abstract class ModelStoreAsync extends ModelStore {

	public <M extends NtroModel> ModelLoader getModel(Class<M> modelClass, String modelId){
		T.call(this);
		
		ModelLoader modelPromise = null;
		
		DocumentPath documentPath = new DocumentPath(modelClass.getSimpleName(), modelId);
		
		JsonLoader promiseJsonObject = getJsonObject(documentPath);
		
		modelPromise = null;
		
		return modelPromise;
	}

	protected abstract JsonLoader getJsonObject(DocumentPath documentPath);

	protected abstract void saveJsonObject(JsonObject jsonObject, DocumentPath documentPath);
	


}
