package ca.ntro.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.messages.NtroModelMessage;
import ca.ntro.stores.DocumentPath;
import ca.ntro.stores.ExternalUpdateListener;
import ca.ntro.stores.ValuePath;

public abstract class ModelStore {

	public static final String MODEL_ID_KEY="_id";
	public static final String MODEL_DATA_KEY="_data";

	private List<DocumentPath> saveHistory = Ntro.collections().synchronizedList(new ArrayList<>());

	private Map<NtroModel, DocumentPath> localHeap = Ntro.collections().concurrentMap(new HashMap<>());
	private Map<DocumentPath, NtroModel> localHeapByPath = Ntro.collections().concurrentMap(new HashMap<>());

	private Map<NtroModel, Set<ModelObserver>> modelObservers = Ntro.collections().concurrentMap(new HashMap<>());
	
	protected abstract boolean ifModelExistsImpl(DocumentPath documentPath);
	
	public ModelStore() {
		ModelLocks.initialize();
	}

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
		
		DocumentPath documentPath = documentPath(modelClass, modelId);
		
		ModelLocks.acquireLockAndExecute(documentPath, new UpdateModelTask<M>(modelClass, authToken, modelId, updater));

		manageHeapLater();
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

				updater.update(model);
				saveModelNow(model);

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
		
		DocumentPath documentPath = documentPath(modelClass, modelId);
		
		ModelLocks.acquireLockAndExecute(documentPath, new CreateModelTask<M>(modelClass, authToken, modelId, initializer));
		
		manageHeapLater();
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

					initializer.initialize(model);
					saveModelNow(model);
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

		DocumentPath documentPath = documentPath(modelClass, modelId);

		ModelLocks.acquireLockAndExecute(documentPath, new ReadModelTask<M>(modelClass, authToken, modelId, reader));

		manageHeapLater();
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

				reader.read(model);

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

		DocumentPath documentPath = documentPath(modelClass, modelId);

		R result =  ModelLocks.acquireLockAndExecute(documentPath, new ExtractFromModelTask<M, R>(modelClass, authToken, modelId, extractor));
		
		manageHeapLater();
		
		return result;
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
					result = extractor.extract(model);
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

	public NtroModel registerModel(DocumentPath documentPath, NtroModel model) throws BackendError {
		T.call(this);

		return ModelLocks.acquireLockAndExecute(documentPath, new ModelLockTask<NtroModel>() {
			@Override
			public NtroModel execute() throws BackendError {
				T.call(this);
				
				NtroModel registeredModel = Ntro.collections().getByKeyEquals(localHeapByPath, documentPath);

				if(registeredModel != null) {

					Log.warning("[registerModel] model already registered: " + documentPath.toString());

				}else {

					registeredModel = model;

					localHeap.put(model, documentPath);
					localHeapByPath.put(documentPath, model);

				}

				return registeredModel;
			}
		});
	}

	public void updateStoreConnections(NtroModel model) {
		T.call(this);
		
		DocumentPath modelPath = Ntro.collections().getByKeyExact(localHeap, model);
		if(modelPath != null) {

			ModelFactory.updateStoreConnections(model, this, modelPath);

		}else {
			Log.warning("[updateStoreConnexions] model not in localHeap: " + model);
		}
	}

	public void updateStoreConnectionsByPath(DocumentPath documentPath) {
		T.call(this);

		NtroModel model = Ntro.collections().getByKeyEquals(localHeapByPath, documentPath);

		if(model != null) {

			ModelFactory.updateStoreConnections(model, this, documentPath);

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
			
			Set<ModelObserver> observers = Ntro.collections().getByKeyExact(modelObservers, model);
			if(observers != null) {
				for(ModelObserver observer : observers) {
					observer.onModelUpdate(model);
				}
			}
		}
	}

	public abstract void onValueMethodInvoked(ValuePath valuePath, String methodName, List<Object> args);

	public void saveModelNow(NtroModel model) throws BackendError {
		T.call(this);
		
		if(model != null) {
			DocumentPath documentPath = Ntro.collections().getByKeyExact(localHeap, model);

			if(documentPath != null) {
				saveModelNow(model, documentPath);
			}

		}
	}

	public void manageHeapLater() throws BackendError {
		T.call(this);

		Ntro.threadService().executeLater(new NtroTaskSync() {
			@Override
			protected void runTask() {
				T.call(this);

				try {
					
					if(localHeap.size() > maxHeapSize()) {
						removeOldestModelFromHeap();
					}
					
				}catch(BackendError e) {
					
					Log.error("[manageHeap] " + e.getMessage());
				}
			}

			@Override
			protected void onFailure(Exception e) {
			}
		});
	}

	private void saveModelNow(NtroModel model, DocumentPath documentPath) throws BackendError {
		T.call(this);
		
		if(model == null) {
			Log.warning("[saveModelNow] model is null");
			return;
		}
		
		if(Ntro.introspector().ntroClassFromObject(model).ifImplements(DoNotCacheModel.class)) {

			removeModelFromHeap(model, documentPath);

		}else {
			
			saveCachedModelNow(model, documentPath);
		}
	}

	private void saveCachedModelNow(NtroModel model, DocumentPath documentPath) throws BackendError {
		T.call(this);

		synchronized (saveHistory) {

			int index = Ntro.collections().indexOfEquals(saveHistory, documentPath);
			if(index > 0) {
				saveHistory.remove(index);
			}
			saveHistory.add(documentPath);

		}

		ModelLocks.acquireLockAndExecute(documentPath, new ModelLockTask<Void>() {
			@Override
			public Void execute() throws BackendError {
				T.call(this);

				saveDocument(documentPath, Ntro.jsonService().toString(model));
				
				return null;
			}
		});
	}

	private void removeOldestModelFromHeap() throws BackendError {
		T.call(this);
		

		DocumentPath documentPath;

		synchronized (saveHistory) {

			if(saveHistory.size() > 0) {

				documentPath = saveHistory.remove(0);

			}else {
				
				documentPath = null;
				
			}
		}

		if(documentPath != null) {
			
			ModelLocks.acquireLockAndExecute(documentPath, new ModelLockTask<Void>() {
				@Override
				public Void execute() throws BackendError {
					NtroModel model = Ntro.collections().getByKeyEquals(localHeapByPath, documentPath);

					if(model != null) {
						saveCachedModelNow(model, documentPath);
						removeModelFromHeap(model, documentPath);
					}

					return null;
				}
			});
		}
	}

	private void removeModelFromHeap(NtroModel model, DocumentPath documentPath) throws BackendError {
		T.call(this);

		if(model == null) {
			Log.warning("[removeModelFromHeap] model is null");
			return;
		}

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

	public <M extends NtroModel> void deleteModel(Class<M> modelClass, 
												  String authToken,
			                                      String documentId) throws BackendError {
		T.call(this);
		
		DocumentPath documentPath = documentPath(modelClass, documentId);

		ModelLocks.acquireLockAndExecute(documentPath, new DeleteModelTask<M>(modelClass, documentPath));
	}

	// JSWEET: compile error (cannot find 'M') when this is an anonymous class
	private class DeleteModelTask<M extends NtroModel> implements ModelLockTask<Void> {
		
		private final DocumentPath documentPath;

		public DeleteModelTask(Class<M> modelClass, DocumentPath documentPath) {
			T.call(this);
			
			this.documentPath = documentPath;
		}

		@Override
		public Void execute() throws BackendError {
			T.call(this);
			
			@SuppressWarnings("unchecked")
			M model = (M) Ntro.collections().getByKeyEquals(localHeapByPath, documentPath);
			
			if(model != null) {
				removeModelFromHeap(model, documentPath);
				deleteDocument(documentPath);
			}
			

			return null;
		}
	}
	
	protected abstract void deleteDocument(DocumentPath documentPath);
	
	public void removeObservers(NtroModel model) {
		T.call(this);
		
		Set<ModelObserver> observers = Ntro.collections().getByKeyExact(modelObservers, model);

		if(observers != null) {
			observers.clear();
		}
	}

	public void removeObserver(NtroModel model, ModelObserver observer) {
		T.call(this);
		
		Set<ModelObserver> observers = Ntro.collections().getByKeyExact(modelObservers, model);
		
		if(observers != null) {
			boolean removed = observers.remove(observer);
			System.out.println("[removeObserver] " + removed);
		}
	}

	public void observeModel(NtroModel model, ModelObserver observer) {
		T.call(this);

		Set<ModelObserver> observers = Ntro.collections().getByKeyExact(modelObservers, model);
		if(observers == null) {
			observers = Ntro.collections().concurrentSet(new HashSet<>());
			modelObservers.put(model, observers);
		}
		observers.add(observer);
		
		if(Ntro.collections().containsKeyExact(localHeap, model)) {
			observer.onModelUpdate(model);
		}
	}

	void forEachModelId(Class<? extends NtroModel> modelClass, 
			            String authToken,
			            ModelIdReader reader) throws BackendError {
		T.call(this);
		
		forEachDocumentIdImpl(Ntro.introspector().getSimpleNameForClass(modelClass), reader);
	}

	protected abstract void forEachDocumentIdImpl(String collectionName, ModelIdReader reader) throws BackendError;
		


}
