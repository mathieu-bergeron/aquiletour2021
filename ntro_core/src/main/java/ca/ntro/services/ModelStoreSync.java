package ca.ntro.services;

import ca.ntro.backend.BackendError;
import ca.ntro.core.Path;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelReader;
import ca.ntro.core.models.ModelReducer;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.stores.DocumentPath;
import ca.ntro.users.NtroSession;

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

	public void saveJsonString(DocumentPath documentPath, String jsonString) {
		T.call(this);

		modelStore.saveDocument(documentPath, jsonString);
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

	public <M extends NtroModel> void deleteModel(Class<M> modelClass, 
												  String authToken,
			                                      Path modelPath) {
		T.call(this);

		modelStore.deleteModel(modelClass, authToken, modelPath);
	}

	public <M extends NtroModel> void updateModel(Class<M> modelClass, 
												  String authToken,
			                                      Path modelPath, 
			                                      ModelUpdater<M> updater) throws BackendError {
		T.call(this);

		updateModel(modelClass, authToken, modelStore.documentId(modelPath), updater);
	}

	public <M extends NtroModel> void readModel(Class<M> modelClass, 
												String authToken,
			                                    Path modelPath, 
			                                    ModelReader<M> reader) throws BackendError {
		T.call(this);

		readModel(modelClass, authToken, modelStore.documentId(modelPath), reader);
	}

	public <M extends NtroModel, ACC extends Object> ACC reduceModel(Class<M> modelClass, 
												                     String authToken,
			                                                         Path modelPath, 
			                                                         Class<ACC> accumulatorClass,
			                                                         ACC accumulator,
			                                                         ModelReducer<M,ACC> reducer) {
		T.call(this);

		return reduceModel(modelClass, 
				           authToken, 
				           modelStore.documentId(modelPath), 
				           accumulatorClass,
				           accumulator,
				           reducer);
	}

	public <M extends NtroModel, ACC extends Object> ACC reduceModel(Class<M> modelClass, 
												                     String authToken,
												                     String modelId,
			                                                         Class<ACC> accumulatorClass,
			                                                         ACC accumulator,
			                                                         ModelReducer<M,ACC> reducer) {
		T.call(this);
		
		return modelStore.reduceModel(modelClass, authToken, modelId, accumulatorClass, accumulator, reducer);
	}
	
	public <M extends NtroModel> void updateModel(Class<M> modelClass, 
												  String authToken,
			                                      String modelId, 
			                                      ModelUpdater<M> updater) throws BackendError {
		T.call(this);
		
		modelStore.updateModel(modelClass, authToken, modelId, updater);
	}

	public <M extends NtroModel> void readModel(Class<M> modelClass, 
												String authToken,
			                                    String modelId, 
			                                    ModelReader<M> reader) throws BackendError {
		T.call(this);
		
		modelStore.readModel(modelClass, authToken, modelId, reader);
	}

	public <M extends NtroModel> void createModel(Class<M> modelClass, 
												  String authToken,
			                                      Path modelPath, 
			                                      ModelInitializer<M> initializer) throws BackendError{
		T.call(this);
		
		createModel(modelClass, authToken, modelStore.documentId(modelPath), initializer);
	}

	public <M extends NtroModel> void createModel(Class<M> modelClass, 
												  String authToken,
			                                      String modelId, 
			                                      ModelInitializer<M> initializer) throws BackendError{
		T.call(this);
		
		modelStore.createModel(modelClass, authToken, modelId, initializer);
	}

	public <M extends NtroModel> M getModel(Class<M> modelClass, String authToken, Path modelPath) {
		T.call(this);
		
		return getModel(modelClass, authToken, modelStore.documentId(modelPath));
	}

	public <M extends NtroModel> M getModel(Class<M> modelClass, String authToken, String modelId) {
		T.call(this);
		
		M model = null;
		
		if(modelStore.ifModelExists(modelClass, authToken, modelId)) {
			
			model = modelStore.getModel(modelClass, authToken, modelId);
		}

		return model;
	}

	public void save(NtroModel model) {
		T.call(this);
		
		modelStore.save(model);
	}
}
