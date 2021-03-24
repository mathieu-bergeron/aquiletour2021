package ca.ntro.services;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.core.introspection.NtroClass;
import ca.ntro.core.introspection.NtroMethod;
import ca.ntro.core.json.Constants;
import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.models.ModelFactory;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.listeners.ValueListener;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.stores.DocumentPath;
import ca.ntro.stores.ExternalUpdateListener;
import ca.ntro.stores.ValuePath;
import ca.ntro.users.NtroUser;

public abstract class ModelStore {

	public static final String MODEL_ID_KEY="modelId";
	public static final String MODEL_DATA_KEY="modelData";
	
	private Map<NtroModel, DocumentPath> localHeap = new HashMap<>();
	private Map<DocumentPath, NtroModel> localHeapByPath = new HashMap<>();
	
	protected abstract boolean ifModelExistsImpl(DocumentPath documentPath);
	
	public boolean ifModelExists(Class<? extends NtroModel> modelClass, String authToken, String firstPathName, String... pathRemainder) {
		T.call(this);

		String documentId = documentId(firstPathName, pathRemainder);
		DocumentPath documentPath = documentPath(modelClass, documentId);

		return ifModelExistsImpl(documentPath);
	}

	public <M extends NtroModel> ModelLoader getLoader(Class<M> modelClass, String authToken, String firstPathName, String... pathRemainder){
		T.call(this);

		ModelLoader modelLoader = new ModelLoader(this);
		
		String documentId = documentId(firstPathName, pathRemainder);
		DocumentPath documentPath = documentPath(modelClass, documentId);
		
		JsonLoader jsonLoader = getJsonLoader(documentPath);
		jsonLoader.setTaskId("JsonLoader");

		modelLoader.setTargetClass(modelClass);

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

	private String documentId(String firstPathName, String... pathRemainder) {
		String documentId = firstPathName;
		for(String additionalSegment : pathRemainder) {
			documentId += "__" + additionalSegment;
		}
		return documentId;
	}
	
	public static String emptyModelString(DocumentPath documentPath) {
		return "{\""+Constants.JSON_CLASS_KEY+"\":\""+documentPath.getCollection()+"\"}";
	}

	public abstract void addValueListener(ValuePath valuePath, ValueListener valueListener);

	// XXX: value could be a JsonObjectIO or a plain Java value
	public abstract <V extends Object> void setValue(ValuePath valuePath, V value);


	protected abstract void installExternalUpdateListener(ExternalUpdateListener updateListener);

	protected abstract JsonLoader getJsonLoader(DocumentPath documentPath);

	public abstract void saveDocument(DocumentPath documentPath, String jsonString);

	public abstract void close();

	public void registerModel(DocumentPath documentPath, NtroModel ntroModel) {
		localHeap.put(ntroModel, documentPath);
		localHeapByPath.put(documentPath, ntroModel);
	}

	public void invokeValueMethod(ValuePath valuePath, String methodName, List<Object> args) {
		if(valuePath == null) return;

		if(args.size() > 0) {
			System.out.println("onValueMethodInvoked: " + valuePath + " " + methodName + " " + args.get(0));
		}else {
			System.out.println("onValueMethodInvoked: " + valuePath + " " + methodName);
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
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					Log.fatalError("Unable to invoke " + methodName + "on valuePath " + valuePath.toString(), e);
				}
			}
		}
	}

	public abstract void onValueMethodInvoked(ValuePath valuePath, String methodName, List<Object> args);

	public abstract void registerThatUserObservesModel(NtroUser user, DocumentPath documentPath, NtroModel model);

	public void save(NtroModel model) {
		T.call(this);
		
		DocumentPath documentPath = localHeap.get(model);
		
		if(documentPath == null) {

			Log.warning("Model was already saved and removed from memory: " + model);

		}else {
			
			saveDocument(documentPath, Ntro.jsonService().toString(model));

			// JSWEET: will the work correctly? (removing by reference)
			localHeap.remove(model);
			localHeapByPath.remove(documentPath);
		}
	}

	public void replace(NtroModel existingModel, NtroModel newModel) {
		DocumentPath documentPath = localHeap.get(existingModel);
		
		if(documentPath == null) {

			Log.warning("Model was already saved and removed from memory: " + existingModel);

		}else {
			
			saveDocument(documentPath, Ntro.jsonService().toString(newModel));

			// JSWEET: will the work correctly? (removing by reference)
			localHeap.remove(existingModel);
			localHeapByPath.remove(documentPath);
		}
	}
	
	void reset() {
		localHeap = new HashMap<>();
		localHeapByPath = new HashMap<>();
	}

	public void delete(NtroModel model) {
		DocumentPath documentPath = localHeap.get(model);
		
		if(documentPath == null) {

			Log.warning("Model was already saved and removed from memory: " + model);

		}else {
			
			deleteDocument(documentPath);

			// JSWEET: will the work correctly? (removing by reference)
			localHeap.remove(model);
			localHeapByPath.remove(documentPath);
		}
		
	}

	protected abstract void deleteDocument(DocumentPath documentPath);

	public void closeWithoutSaving(NtroModel existingModel) {
		T.call(this);

		DocumentPath documentPath = localHeap.get(existingModel);

		// JSWEET: will the work correctly? (removing by reference)
		localHeap.remove(existingModel);
		localHeapByPath.remove(documentPath);
	}
}
