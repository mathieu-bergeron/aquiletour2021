package ca.ntro.core.models;

import ca.ntro.core.introspection.Factory;
import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.json.JsonObject;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;

public class ModelLoader extends NtroTaskImpl {
	
	private Class<? extends NtroModel> modelClass;
	
	private NtroModel model;
	private ModelStore modelStore;
	
	public ModelLoader(ModelStore modelStore) {
		super();
		T.call(this);
		
		this.modelStore = modelStore;
	}
	

	@Override
	protected void initializeTask() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);

		JsonLoader jsonLoader = (JsonLoader) getPreviousTask(JsonLoader.class, "JsonLoader");
		
		JsonObject jsonObject = jsonLoader.getJsonObject();
		
		model = Factory.newInstance(modelClass);

		model.setOrigin(modelStore);
		
		model.loadFromJsonObject(jsonObject);
		
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
