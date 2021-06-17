package ca.ntro.services;

import ca.ntro.backend.BackendError;
import ca.ntro.core.Path;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelReader;
import ca.ntro.core.models.ModelExtractor;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.listeners.ValueObserver;
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

	public boolean ifModelExists(Class<? extends NtroModel> modelClass, String authToken, Path modelPath) throws BackendError {
		T.call(this);

		return ifModelExists(modelClass, authToken, modelStore.documentId(modelPath));
	}

	public boolean ifModelExists(Class<? extends NtroModel> modelClass, String authToken, String documentId) throws BackendError {
		T.call(this);

		return modelStore.ifModelExists(modelClass, authToken, documentId);
	}

	public void saveJsonString(DocumentPath documentPath, String jsonString) {
		T.call(this);

		modelStore.saveDocument(documentPath, jsonString);
	}

	public <M extends NtroModel> void deleteModel(Class<? extends NtroModel> modelClass, 
												  String authToken,
			                                      String documentId) throws BackendError {
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

	public <M extends NtroModel, R extends Object> R reduceModel(Class<M> modelClass, 
												                     String authToken,
			                                                         Path modelPath, 
			                                                         Class<R> extractedValueClass,
			                                                         ModelExtractor<M,R> extractor) throws BackendError {
		T.call(this);

		return extractFromModel(modelClass, 
				               authToken, 
				               modelStore.documentId(modelPath), 
				               extractedValueClass,
				               extractor);
	}

	public <M extends NtroModel, R extends Object> R extractFromModel(Class<M> modelClass, 
												                      String authToken,
												                      String modelId,
			                                                          Class<R> extractedValueClass,
			                                                          ModelExtractor<M,R> extractor) throws BackendError {
		T.call(this);
		
		return modelStore.extractFromModel(modelClass, authToken, modelId, extractedValueClass, extractor);
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

	public void forEachModelId(Class<? extends NtroModel> modelClass, 
			                   String authToken,
			                   ModelIdReader reader) throws BackendError {
		T.call(this);
		
		modelStore.forEachModelId(modelClass, authToken, reader);
	}

	public void removeObservers(NtroModel model) {
		T.call(this);
		
		modelStore.removeObservers(model);
	}

	public void removeObserver(NtroModel model, ModelObserver observer) {
		T.call(this);
		
		modelStore.removeObserver(model, observer);
	}

	public ModelObserver observeModel(NtroModel model, ModelObserver observer) {
		T.call(this);
		
		modelStore.observeModel(model, observer);
		
		return observer;
	}

}
