package ca.ntro.core.models;

import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.services.stores.DocumentPath;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskAsync;

public class ModelLoader extends NtroTaskAsync {
	
	private Class<? extends NtroModel> modelClass;
	
	private NtroModel model;
	private ModelStore modelStore;
	
	public ModelLoader(ModelStore modelStore) {
		super();
		T.call(this);
		
		this.modelStore = modelStore;
	}
	
	@Override
	protected void runTaskAsync() {
		T.call(this);

		//JsonLoader jsonLoader = (JsonLoader) getPreviousTask(JsonLoader.class, "JsonLoader");
		JsonLoader jsonLoader = (JsonLoader) getSubTask(JsonLoader.class, "JsonLoader");
		
		String jsonString = jsonLoader.getJsonString();
		DocumentPath documentPath = jsonLoader.getDocumentPath();
		
		model = ModelFactory.createModel(modelClass, modelStore, documentPath, jsonString);
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

	public NtroModel getModel() {
		T.call(this);

		return model;
	}

	public void setTargetClass(Class<? extends NtroModel> modelClass) {
		T.call(this);

		this.modelClass = modelClass;
	}

}
