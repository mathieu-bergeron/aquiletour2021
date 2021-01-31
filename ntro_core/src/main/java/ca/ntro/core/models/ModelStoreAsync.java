package ca.ntro.core.models;

import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.json.JsonObject;
import ca.ntro.core.models.stores.DocumentPath;
import ca.ntro.core.system.trace.T;


public abstract class ModelStoreAsync extends ModelStore {

	public <M extends NtroModel> ModelLoader getLoaderImpl(Class<M> modelClass, String modelId){
		T.call(this);
		
		ModelLoader modelLoader = new ModelLoader(this);
		
		DocumentPath documentPath = new DocumentPath(modelClass.getSimpleName(), modelId);
		
		JsonLoader jsonLoader = getJsonObject(documentPath);
		jsonLoader.setTaskId("JsonLoader");

		modelLoader.setTargetClass(modelClass);
		
		modelLoader.addPreviousTask(jsonLoader);
		
		return modelLoader;
	}

	protected abstract JsonLoader getJsonObject(DocumentPath documentPath);

	protected abstract void saveJsonObject(JsonObject jsonObject, DocumentPath documentPath);
	
	public abstract void close();
	


}
