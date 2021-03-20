package ca.ntro.core.models;

import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStore;
import ca.ntro.stores.DocumentPath;

public class ModelStoreSync {

	private ModelStore modelStore;
	
	public ModelStoreSync(ModelStore modelStore) {
		this.modelStore = modelStore;
	}

	public boolean ifModelExists(Class<? extends NtroModel> modelClass, String authToken, String firstPathName, String... pathRemainder) {
		T.call(this);

		return modelStore.ifModelExists(modelClass, authToken, firstPathName, pathRemainder);
	}

	@SuppressWarnings("unchecked")
	public <M extends NtroModel> M getModel(Class<M> modelClass, String authToken, String firstPathName, String... pathRemainder) {

		ModelLoader loader = modelStore.getLoader(modelClass, authToken, firstPathName, pathRemainder);
		loader.execute();
		
		M model = (M) loader.getModel();
		
		MustNot.beNull(model);

		return model;
	}

	public void save(NtroModel model) {
		T.call(this);

		modelStore.save(model);
	}

	public void saveJsonString(DocumentPath documentPath, String jsonString) {
		T.call(this);

		modelStore.saveDocument(documentPath, jsonString);
	}

	public void replace(NtroModel existingModel, NtroModel newModel) {
		T.call(this);

		modelStore.replace(existingModel, newModel);
	}

	public void delete(NtroModel model) {
		T.call(this);

		modelStore.delete(model);
	}
	
	public <M extends NtroModel> void updateModel(Class<? extends NtroModel> modelClass, 
												  String authToken,
			                                      String firstPathName, 
			                                      ModelUpdater<M> updater){

		if(ifModelExists(modelClass, firstPathName, firstPathName)) {
			
			M model = (M) getModel(modelClass, authToken, firstPathName);

			updater.update(model);
			
			save(model);
		}
	}

	public <M extends NtroModel> void createModel(Class<? extends NtroModel> modelClass, 
												  String authToken,
			                                      String firstPathName, 
			                                      ModelInitializer<M> initializer){

		M model = (M) getModel(modelClass, authToken, firstPathName);

		initializer.initialize(model);
			
		save(model);
	}
	
	
}
