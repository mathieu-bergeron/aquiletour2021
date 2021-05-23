package ca.ntro.core.models;

import ca.ntro.backend.BackendError;
import ca.ntro.core.Path;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStore;
import ca.ntro.services.Ntro;
import ca.ntro.stores.DocumentPath;

public class ModelStoreSync {

	private ModelStore modelStore;
	
	public ModelStoreSync(ModelStore modelStore) {
		this.modelStore = modelStore;
	}

	public boolean ifModelExists(Class<? extends NtroModel> modelClass, String authToken, Path modelPath) {
		T.call(this);

		return ifModelExists(modelClass, authToken, modelStore.documentId(modelPath));
	}

	public boolean ifModelExists(Class<? extends NtroModel> modelClass, String authToken, String documentId) {
		T.call(this);

		return modelStore.ifModelExists(modelClass, authToken, documentId);
	}

	@SuppressWarnings("unchecked")
	public <M extends NtroModel> M getModel(Class<M> modelClass, String authToken, String documentId) {

		ModelLoader loader = modelStore.getLoader(modelClass, authToken, documentId);
		loader.execute();
		
		M model = (M) loader.getModel();
		
		MustNot.beNull(model);

		return model;
	}

	public <M extends NtroModel> M getModel(Class<M> modelClass, String authToken, Path modelPath) {
		return getModel(modelClass, authToken, modelStore.documentId(modelPath));
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

	public <M extends NtroModel> void deleteModel(Class<? extends NtroModel> modelClass, 
												  String authToken,
			                                      String documentId) {
		T.call(this);
		
		modelStore.deleteModel(modelClass, authToken, documentId);
	}

	public <M extends NtroModel> void deleteModel(Class<? extends NtroModel> modelClass, 
												  String authToken,
			                                      Path modelPath) {
		T.call(this);

		modelStore.deleteModel(modelClass, authToken, modelPath);
	}

	public <M extends NtroModel> void updateModel(Class<? extends NtroModel> modelClass, 
												  String authToken,
			                                      Path modelPath, 
			                                      ModelUpdater<M> updater) throws BackendError {
		T.call(this);

		updateModel(modelClass, authToken, modelStore.documentId(modelPath), updater);
	}
	
	public <M extends NtroModel> void updateModel(Class<? extends NtroModel> modelClass, 
												  String authToken,
			                                      String modelId, 
			                                      ModelUpdater<M> updater) throws BackendError {
		T.call(this);

		if(ifModelExists(modelClass, authToken, modelId)) {
			
			M model = (M) getModel(modelClass, authToken, modelId);
			
			updater.update(model);
			
			save(model);

		}else {
			Log.warning("model not found: " + Ntro.introspector().getSimpleNameForClass(modelClass) + "/" + modelId);
		}
	}

	public <M extends NtroModel> void createModel(Class<? extends NtroModel> modelClass, 
												  String authToken,
			                                      Path modelPath, 
			                                      ModelInitializer<M> initializer){
		T.call(this);
		
		createModel(modelClass, authToken, modelStore.documentId(modelPath), initializer);
	}

	public <M extends NtroModel> void createModel(Class<? extends NtroModel> modelClass, 
												  String authToken,
			                                      String modelId, 
			                                      ModelInitializer<M> initializer){
		T.call(this);

		M model = (M) getModel(modelClass, authToken, modelId);

		initializer.initialize(model);
			
		save(model);
	}

	public void closeWithoutSaving(NtroModel model) {
		T.call(this);
		
		modelStore.closeWithoutSaving(model);
	}
	
	
}
