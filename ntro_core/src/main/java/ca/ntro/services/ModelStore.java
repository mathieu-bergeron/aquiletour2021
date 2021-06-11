package ca.ntro.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.backend.BackendError;
import ca.ntro.core.Path;
import ca.ntro.core.introspection.NtroClass;
import ca.ntro.core.introspection.NtroMethod;
import ca.ntro.core.json.Constants;
import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.models.ModelFactory;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.ModelReader;
import ca.ntro.core.models.ModelExtractor;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.DoNotCacheModel;
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

	private List<DocumentPath> saveHistory = Ntro.collections().synchronizedList(new ArrayList<>());

	private Map<NtroModel, DocumentPath> localHeap = Ntro.collections().concurrentMap(new HashMap<>());
	private Map<DocumentPath, NtroModel> localHeapByPath = Ntro.collections().concurrentMap(new HashMap<>());

	protected abstract boolean ifModelExistsImpl(DocumentPath documentPath);

	public boolean ifModelExists(Class<? extends NtroModel> modelClass, String authToken, String documentId) throws BackendError {
		T.call(this);
		
		DocumentPath documentPath = documentPath(modelClass, documentId);
		
		return ModelLocks.acquireLockAndExecute(documentPath, new ModelLockTask<Boolean>() {
			@Override
			public Boolean execute() {

				boolean ifExists = false;

				ifExists = Ntro.collections().containsKeyEquals(localHeapByPath, documentPath);

				if(!ifExists) {
					
					ifExists = ifModelExistsImpl(documentPath);
				}
				
				return ifExists;
			}
		});
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

		ModelLoader modelLoader = null;

		NtroModel model = Ntro.collections().getByKeyEquals(localHeapByPath, documentPath);
		if(model != null) {
			modelLoader = new ModelLoaderMemory(model);
		}

		if(modelLoader == null) {

			modelLoader = new ModelLoader(this, documentPath);
			
			JsonLoader jsonLoader = getJsonLoader(modelClass,documentPath);
			jsonLoader.setTaskId("JsonLoader");

			modelLoader.setTargetClass(modelClass);

			//modelLoader.addPreviousTask(jsonLoader);
			modelLoader.addSubTask(jsonLoader);
		}

		return modelLoader;
	}

	public ModelLoader getLoaderFromRequest(String serviceUrl, NtroModelMessage message) {
		T.call(this);
		
		ModelLoader modelLoader = null;
		
		NtroModel model = Ntro.collections().getByKeyEquals(localHeapByPath, message.getDocumentPath());
		if(model != null) {
			modelLoader = new ModelLoaderMemory(model);
		}
		
		if(modelLoader != null){

			modelLoader = new ModelLoader(this, message.getDocumentPath());
			
			JsonLoader jsonLoader = jsonLoaderFromRequest(serviceUrl, message);
			jsonLoader.setTaskId("JsonLoader");

			modelLoader.setTargetClass(message.targetClass());

			//modelLoader.addPreviousTask(jsonLoader);
			modelLoader.addSubTask(jsonLoader);
		}

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
	
	@SuppressWarnings("unchecked") 
	<M extends NtroModel> M getModel(Class<M> modelClass, String authToken, String documentId) {
		T.call(this);

		ModelLoader loader = getLoader(modelClass, authToken, documentId);
		loader.execute();
		
		M model = (M) loader.getModel();
		
		MustNot.beNull(model);

		return model;
	}
	

	<M extends NtroModel> void updateModel(Class<M> modelClass, 
										   String authToken,
			                               String modelId, 
			                               ModelUpdater<M> updater) throws BackendError {
		T.call(this);
		
		ModelLocks.acquireLockAndExecute(documentPath(modelClass, modelId), new UpdateModelTask<M>(modelClass, authToken, modelId, updater));
	}

	// JSWEET: compile error (cannot find 'M') when this is an anonymous class
	private class UpdateModelTask<M extends NtroModel> implements ModelLockTask<Void> {
		private Class<M> modelClass;
		private String authToken;
		private String modelId;
		private ModelUpdater<M> updater;

		public UpdateModelTask(Class<M> modelClass, String authToken, String modelId, ModelUpdater<M> updater) {
			T.call(this);
			
			this.modelClass = modelClass;
			this.authToken = authToken;
			this.modelId = modelId;
			this.updater = updater;
		}

		@Override
		public Void execute() throws BackendError {

			M model = null;
			if(ifModelExists(modelClass, authToken, modelId)) {
				model = getModel(modelClass, authToken, modelId);
			}

			if(model != null) {
				synchronized (model) {
					updater.update(model);
					saveModelNow(model);
				}

			}else {

				Log.warning("[updateModel] model not found: " + documentPath(modelClass, modelId));
			}

			return null;
		}
	}

	<M extends NtroModel> void createModel(Class<M> modelClass, 
										   String authToken,
			                               String modelId, 
			                               ModelInitializer<M> initializer) throws BackendError {
		T.call(this);
		
		ModelLocks.acquireLockAndExecute(documentPath(modelClass, modelId), new CreateModelTask<M>(modelClass, authToken, modelId, initializer));
	}

	// JSWEET: compile error (cannot find 'M') when this is an anonymous class
	private class CreateModelTask<M extends NtroModel> implements ModelLockTask<Void> {
		private Class<M> modelClass;
		private String authToken;
		private String modelId;
		private ModelInitializer<M> initializer;

		public CreateModelTask(Class<M> modelClass, String authToken, String modelId, ModelInitializer<M> initializer) {
			T.call(this);
			
			this.modelClass = modelClass;
			this.authToken = authToken;
			this.modelId = modelId;
			this.initializer = initializer;
		}

		@Override
		public Void execute() throws BackendError {
			T.call(this);

			if(!ifModelExists(modelClass, authToken, modelId)) {

				M model = getModel(modelClass, authToken, modelId);

				if(model != null) {
					synchronized (model) {
						initializer.initialize(model);
						saveModelNow(model);
					}
				}

			}else {
				Log.warning("[createModel] model already exists: " + documentPath(modelClass, modelId));
			}

			return null;
		}
	}


	<M extends NtroModel> void readModel(Class<M> modelClass, 
									     String authToken,
			                             String modelId, 
			                             ModelReader<M> reader) throws BackendError {
		T.call(this);

		ModelLocks.acquireLockAndExecute(documentPath(modelClass, modelId), new ReadModelTask<M>(modelClass, authToken, modelId, reader));
	}

	// JSWEET: compile error (cannot find 'M') when this is an anonymous class
	private class ReadModelTask<M extends NtroModel> implements ModelLockTask<Void> {
		private Class<M> modelClass;
		private String authToken;
		private String modelId;
		private ModelReader<M> reader;

		public ReadModelTask(Class<M> modelClass, String authToken, String modelId, ModelReader<M> reader) {
			T.call(this);
			
			this.modelClass = modelClass;
			this.authToken = authToken;
			this.modelId = modelId;
			this.reader = reader;
		}

		@Override
		public Void execute() throws BackendError {
			T.call(this);

			if(ifModelExists(modelClass, authToken, modelId)) {

				M model = getModel(modelClass, authToken, modelId);

				synchronized (model) {

					reader.read(model);
				}

			}else {

				Log.warning("[readModel] model not found: " + documentPath(modelClass, modelId));
			}

			return null;
		}
	}

	<M extends NtroModel, R extends Object> R extractFromModel(Class<M> modelClass, 
												               String authToken,
												               String modelId,
			                                                   Class<R> accumulatorClass,
			                                                   ModelExtractor<M,R> extractor) throws BackendError {
		T.call(this);

		return ModelLocks.acquireLockAndExecute(documentPath(modelClass, modelId), new ExtractFromModelTask<M, R>(modelClass, authToken, modelId, extractor));
	}

	// JSWEET: compile error (cannot find 'M') when this is an anonymous class
	private class ExtractFromModelTask<M extends NtroModel, R extends Object> implements ModelLockTask<R> {
		private Class<M> modelClass;
		private String authToken;
		private String modelId;
		private ModelExtractor<M,R> extractor;

		public ExtractFromModelTask(Class<M> modelClass, String authToken, String modelId, ModelExtractor<M,R> extractor) {
			T.call(this);
			
			this.modelClass = modelClass;
			this.authToken = authToken;
			this.modelId = modelId;
			this.extractor = extractor;
		}

		@Override
		public R execute() throws BackendError {
			R result = null;

			if(ifModelExists(modelClass, authToken, modelId)) {

				M model =  getModel(modelClass, authToken, modelId);

				if(model != null) {

					synchronized (model) {
						result = extractor.extract(model);
					}
				}

			}else {

				Log.warning("model not found: " + documentPath(modelClass, modelId));
			}
			
			return result;
		}
	}

	public abstract void addValueListener(ValuePath valuePath, ValueListener valueListener);

	// XXX: value could be a JsonObjectIO or a plain Java value
	public abstract <V extends Object> void setValue(ValuePath valuePath, V value);


	protected abstract void installExternalUpdateListener(ExternalUpdateListener updateListener);

	protected abstract JsonLoader getJsonLoader(Class<? extends NtroModel> targetClass, DocumentPath documentPath);

	protected abstract JsonLoader jsonLoaderFromRequest(String serviceUrl, NtroModelMessage message);

	public abstract void saveDocument(DocumentPath documentPath, String jsonString);

	public abstract void close();

	public void registerModel(DocumentPath documentPath, NtroModel model) throws BackendError {
		T.call(this);

		if(Ntro.collections().containsKeyEquals(localHeapByPath, documentPath)){
			Log.warning("[registerModel] model already registered: " + documentPath.toString());
		}
		
		ModelLocks.acquireLockAndExecute(documentPath, new ModelLockTask<Void>() {
			@Override
			public Void execute() throws BackendError {
				T.call(this);

				localHeap.put(model, documentPath);
				localHeapByPath.put(documentPath, model);

				return null;
			}
		});
	}

	public void updateStoreConnections(NtroModel model) {
		T.call(this);
		
		DocumentPath modelPath = Ntro.collections().getByKeyExact(localHeap, model);
		if(modelPath != null) {

			synchronized (model) {
				ModelFactory.updateStoreConnections(model, this, modelPath);
			}

		}else {
			Log.warning("[updateStoreConnexions] model not in localHeap: " + model);
		}
	}

	public void updateStoreConnectionsByPath(DocumentPath documentPath) {
		T.call(this);

		NtroModel model = Ntro.collections().getByKeyEquals(localHeapByPath, documentPath);

		if(model != null) {
			
			synchronized (model) {

				ModelFactory.updateStoreConnections(model, this, documentPath);
			}

		}else {
			Log.warning("No model to update for path: " + documentPath.toString());
		}
	}

	public void invokeValueMethod(ValuePath valuePath, String methodName, List<Object> args) {
		if(valuePath == null) return;

		if(args.size() == 0) {
			System.out.println("invokeValueMethod: " + valuePath + " " + methodName);
		}else if(args.size() == 1){
			System.out.println("invokeValueMethod: " + valuePath + " " + methodName + " " + args.get(0));
		}else if(args.size() == 2){
			System.out.println("invokeValueMethod: " + valuePath + " " + methodName
														+ " " + args.get(0) 
														+ " " + args.get(1));
		}else {
			System.out.println("invokeValueMethod: " + valuePath + " " + methodName
														+ " " + args.get(0) 
														+ " " + args.get(1) 
														+ " " + args.get(2));
		}

		DocumentPath documentPath = valuePath.getDocumentPath();

		NtroModel model = Ntro.collections().getByKeyEquals(localHeapByPath, documentPath);

		if(model != null) {

			synchronized (model) {

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
	}

	public abstract void onValueMethodInvoked(ValuePath valuePath, String methodName, List<Object> args);

	public void saveModelNow(NtroModel model) throws BackendError {
		T.call(this);
		
		if(model != null) {
			synchronized (model) {
				DocumentPath documentPath = Ntro.collections().getByKeyExact(localHeap, model);
				if(documentPath != null) {
					saveModelNow(model, documentPath);
				}
				manageHeap(model, documentPath);
			}
		}
	}

	public void manageHeap(NtroModel model, DocumentPath documentPath) throws BackendError {
		T.call(this);

		if(Ntro.introspector().ntroClassFromObject(model).ifImplements(DoNotCacheModel.class)) {

			removeModelFromHeap(model, documentPath);

		}else {

			synchronized (saveHistory) {
				int index = Ntro.collections().indexOfEquals(saveHistory, documentPath);
				if(index > 0) {
					saveHistory.remove(index);
				}
				saveHistory.add(documentPath);
			}

			if(localHeap.size() > maxHeapSize()) {
				removeOldestModelFromHeap();
			}
		}
	}

	private void saveModelNow(NtroModel model, DocumentPath documentPath) {
		T.call(this);

		if(model != null) {
			synchronized (model) {
				saveDocument(documentPath, Ntro.jsonService().toString(model));
			}
		}
	}

	private void removeOldestModelFromHeap() throws BackendError {
		T.call(this);

		DocumentPath documentPath = saveHistory.remove(0);

		if(documentPath != null) {
			synchronized (localHeap) {
				NtroModel model = Ntro.collections().getByKeyEquals(localHeapByPath, documentPath);

				if(model != null) {
					saveModelNow(model, documentPath);
					removeModelFromHeap(model, documentPath);
				}
			}
		}
	}

	private void removeModelFromHeap(NtroModel model, DocumentPath documentPath) throws BackendError {
		T.call(this);
		
		ModelLocks.acquireLockAndExecute(documentPath, new ModelLockTask<Void>() {

			@Override
			public Void execute() throws BackendError {
				T.call(this);
				
				Ntro.collections().removeByKeyEquals(localHeapByPath, documentPath);
				Ntro.collections().removeByKeyExact(localHeap, model);
				
				ModelLocks.destroyLock(documentPath);

				return null;
			}
			
		});
	}
	
	protected abstract int maxHeapSize();

	public void delete(NtroModel model) {
		DocumentPath documentPath = Ntro.collections().getByKeyExact(localHeap, model);
		
		if(documentPath == null) {

			Log.warning("[delete] model not in localHeap: " + model);

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

}
