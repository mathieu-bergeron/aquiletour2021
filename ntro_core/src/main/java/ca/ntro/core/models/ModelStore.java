package ca.ntro.core.models;

import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.json.JsonObject;
import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.services.stores.DocumentPath;
import ca.ntro.core.services.stores.ExternalUpdateListener;
import ca.ntro.core.services.stores.ValuePath;
import ca.ntro.core.system.trace.T;

public abstract class ModelStore {
	
	public static final String MODEL_ID_KEY="modelId";
	public static final String MODEL_DATA_KEY="modelData";


	public <M extends NtroModel> ModelLoader getLoaderImpl(Class<M> modelClass, String authToken, String firstPathName, String... pathRemainder){
		T.call(this);
		
		ModelLoader modelLoader = new ModelLoader(this);
		
		DocumentPath documentPath = new DocumentPath(modelClass.getSimpleName(), firstPathName, pathRemainder);
		
		JsonLoader jsonLoader = getJsonObject(documentPath);
		jsonLoader.setTaskId("JsonLoader");

		modelLoader.setTargetClass(modelClass);
		
		modelLoader.addPreviousTask(jsonLoader);
		
		return modelLoader;
	}

	public abstract void addValueListener(ValuePath valuePath, ValueListener valueListener);

	// XXX: value could be a JsonObjectIO or a plain Java value
	public abstract <V extends Object> void setValue(ValuePath valuePath, V value);


	protected abstract void installExternalUpdateListener(ExternalUpdateListener updateListener);

	protected abstract JsonLoader getJsonObject(DocumentPath documentPath);

	protected abstract void saveJsonObject(DocumentPath documentPath, JsonObject jsonObject);
	
	public abstract void close();

}
