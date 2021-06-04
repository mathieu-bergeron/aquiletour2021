package ca.ntro.services;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.core.Path;
import ca.ntro.core.introspection.NtroClass;
import ca.ntro.core.introspection.NtroMethod;
import ca.ntro.core.json.Constants;
import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.models.ModelFactory;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.listeners.ValueListener;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroModelMessage;
import ca.ntro.stores.DocumentPath;
import ca.ntro.stores.ExternalUpdateListener;
import ca.ntro.stores.ValuePath;

public abstract class ModelStore {

	public static final String MODEL_ID_KEY="modelId";
	public static final String MODEL_DATA_KEY="modelData";
	
	private Map<NtroModel, DocumentPath> localHeap = Ntro.collections().concurrentMap(new HashMap<>());
	private Map<DocumentPath, NtroModel> localHeapByPath = Ntro.collections().concurrentMap(new HashMap<>());

	protected abstract boolean ifModelExistsImpl(DocumentPath documentPath);

	public boolean ifModelExists(Class<? extends NtroModel> modelClass, String authToken, String documentId) {
		T.call(this);

		DocumentPath documentPath = documentPath(modelClass, documentId);

		return ifModelExistsImpl(documentPath);
	}


	public <M extends NtroModel> void updateModel(Class<? extends NtroModel> modelClass, 
												  String authToken,
			                                      String modelId, 
			                                      ModelUpdater<M> updater){
		T.call(this);
		
		/* TODO: make modelStore threadSafe on the server
		 * 
		 * updateModel:
		 * 
		 * 1. synchronized(localHeapByPath) and get a model object (possibly empty)
		 *      + manage cache (e.g. possible destroy some older models)
		 * 1. synchronized(model):
		 *      + load actual data from DB if necessary
		 *      	+ (we want to synchronize on model here, not localHeapByPath as loading can take time)
		 *      + run the updater
		 *      + queue a saveModel event
		 * 
		 * That way, the modelStore can be accessed from multiple threads
		 * 	    + the model object is always the same
		 * 		+ the model is modified by a single thread at a time
		 * 
		 * 
		 * 
		 */
		
		
	}

	public <M extends NtroModel> ModelLoader getLoader(Class<M> modelClass, String authToken, Path modelPath){
		T.call(this);
		
		return getLoader(modelClass, authToken, documentId(modelPath));
	}

	public String documentId(Path modelPath) {
		T.call(this);
		
		return modelPath.toFileName();
	}

	public <M extends NtroModel> ModelLoader getLoader(Class<M> modelClass, String authToken, String documentId){
		T.call(this);
		
		DocumentPath documentPath = documentPath(modelClass, documentId);

		ModelLoader modelLoader = new ModelLoader(this, documentPath);
		
		JsonLoader jsonLoader = getJsonLoader(modelClass,documentPath);
		jsonLoader.setTaskId("JsonLoader");

		modelLoader.setTargetClass(modelClass);

		//modelLoader.addPreviousTask(jsonLoader);
		modelLoader.addSubTask(jsonLoader);

		return modelLoader;
	}

	public ModelLoader getModelLoaderFromRequest(String serviceUrl, NtroModelMessage message) {
		T.call(this);

		ModelLoader modelLoader = new ModelLoader(this, message.documentPath());
		
		JsonLoader jsonLoader = jsonLoaderFromRequest(serviceUrl, message);
		jsonLoader.setTaskId("JsonLoader");

		modelLoader.setTargetClass(message.targetClass());

		//modelLoader.addPreviousTask(jsonLoader);
		modelLoader.addSubTask(jsonLoader);

		return modelLoader;
	}

	private <M extends NtroModel> DocumentPath documentPath(Class<M> modelClass, String documentId) {
		DocumentPath documentPath = new DocumentPath();

		documentPath.setCollection(Ntro.introspector().getSimpleNameForClass(modelClass));
		documentPath.setDocumentId(documentId);
		return documentPath;
	}
	
	public static String emptyModelString(DocumentPath documentPath) {
		return "{\""+Constants.JSON_CLASS_KEY+"\":\""+documentPath.getCollection()+"\"}";
	}

	public abstract void addValueListener(ValuePath valuePath, ValueListener valueListener);

	// XXX: value could be a JsonObjectIO or a plain Java value
	public abstract <V extends Object> void setValue(ValuePath valuePath, V value);


	protected abstract void installExternalUpdateListener(ExternalUpdateListener updateListener);

	protected abstract JsonLoader getJsonLoader(Class<? extends NtroModel> targetClass, DocumentPath documentPath);

	protected abstract JsonLoader jsonLoaderFromRequest(String serviceUrl, NtroModelMessage message);

	public abstract void saveDocument(DocumentPath documentPath, String jsonString);

	public abstract void close();

	public void registerModel(DocumentPath documentPath, NtroModel model) {
		localHeap.put(model, documentPath);
		localHeapByPath.put(documentPath, model);
	}

	public void updateStoreConnections(NtroModel model) {
		DocumentPath modelPath = Ntro.collections().getExact(localHeap, model);
		
		if(modelPath != null) {

			ModelFactory.updateStoreConnections(model, this, modelPath);

		}else {
			
			Log.warning("[updateStoreConnexionts] model not found in localHeap: " + model);
		}
	}

	public void updateStoreConnectionsByPath(DocumentPath modelPath) {
		NtroModel model = localHeapByPath.get(modelPath);

		if(model != null) {

			ModelFactory.updateStoreConnections(model, this, modelPath);

		}else {
			
			Log.warning("No model to update for path: " + modelPath.toString());
		}
	}

	public void invokeValueMethod(ValuePath valuePath, String methodName, List<Object> args) {
		if(valuePath == null) return;

		if(args.size() == 0) {
			System.out.println("onValueMethodInvoked: " + valuePath + " " + methodName);
		}else if(args.size() == 1){
			System.out.println("onValueMethodInvoked: " + valuePath + " " + methodName + " " + args.get(0));
		}else if(args.size() == 2){
			System.out.println("onValueMethodInvoked: " + valuePath + " " + methodName
														+ " " + args.get(0) 
														+ " " + args.get(1));
		}else {
			System.out.println("onValueMethodInvoked: " + valuePath + " " + methodName
														+ " " + args.get(0) 
														+ " " + args.get(1) 
														+ " " + args.get(2));
		}

		DocumentPath documentPath = valuePath.getDocumentPath();

		NtroModel model = localHeapByPath.get(documentPath);
		MustNot.beNull(model);

		if(model != null) {
			
			Object value = Ntro.introspector().findByValuePath(model, valuePath);

			if(value != null) {
				NtroClass valueClass = Ntro.introspector().ntroClassFromObject(value);
				NtroMethod methodToCall = valueClass.methodByName(methodName);

				try {

					methodToCall.invoke(value, args);
					
					// XXX: if we add a NtroModelValue, we need to connect it to the store
					ModelFactory.updateStoreConnections(model, this, documentPath);
					
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					Log.fatalError("Unable to invoke " + methodName + " on valuePath " + valuePath.toString(), e);
				}
			}
		}
	}

	public abstract void onValueMethodInvoked(ValuePath valuePath, String methodName, List<Object> args);

	public void save(NtroModel model) {
		T.call(this);
		
		DocumentPath documentPath = Ntro.collections().getExact(localHeap, model);
		
		if(documentPath == null) {

			Log.error("[save] model not found in localHeap: " + model);

		}else {

			saveDocument(documentPath, Ntro.jsonService().toString(model));
		}
	}

	public void replace(NtroModel existingModel, NtroModel newModel) {
		DocumentPath documentPath = Ntro.collections().getExact(localHeap, existingModel);
		
		if(documentPath == null) {

			Log.warning("[replace] model not found in localHeap: " + existingModel);

		}else {
			
			saveDocument(documentPath, Ntro.jsonService().toString(newModel));
		}
	}
	
	void reset() {
		localHeap = new HashMap<>();
		localHeapByPath = new HashMap<>();
	}

	public void delete(NtroModel model) {
		DocumentPath documentPath = Ntro.collections().getExact(localHeap, model);
		
		if(documentPath == null) {

			Log.warning("[delete] model not found in localHeap: " + model);

		}else {
			
			deleteDocument(documentPath);

			// JSWEET: will the work correctly? (removing by reference)
			localHeap.remove(model);
			localHeapByPath.remove(documentPath);
		}
		
	}

	public <M extends NtroModel> void deleteModel(Class<? extends NtroModel> modelClass, 
												  String authToken,
			                                      String documentId) {
		T.call(this);
		
		deleteDocument(documentPath(modelClass, documentId));
	}

	public <M extends NtroModel> void deleteModel(Class<? extends NtroModel> modelClass, 
												  String authToken,
			                                      Path modelPath) {
		T.call(this);

		deleteDocument(documentPath(modelClass, documentId(modelPath)));
	}

	protected abstract void deleteDocument(DocumentPath documentPath);

	public void closeWithoutSaving(NtroModel model) {
		T.call(this);

		DocumentPath documentPath = Ntro.collections().getExact(localHeap, model);

		// JSWEET: will the work correctly? (removing by reference)
		//localHeap.remove(model);
		//localHeapByPath.remove(documentPath);
	}
}
