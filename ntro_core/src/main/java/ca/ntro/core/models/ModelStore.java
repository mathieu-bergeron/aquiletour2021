package ca.ntro.core.models;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.core.NtroUser;
import ca.ntro.core.introspection.NtroClass;
import ca.ntro.core.introspection.NtroMethod;
import ca.ntro.core.json.Constants;
import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.models.listeners.ValueListener;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.stores.DocumentPath;
import ca.ntro.stores.ExternalUpdateListener;
import ca.ntro.stores.ValuePath;

public abstract class ModelStore {

	public static final String MODEL_ID_KEY="modelId";
	public static final String MODEL_DATA_KEY="modelData";
	
	private Map<NtroModel, DocumentPath> localHeap = new HashMap<>();
	private Map<DocumentPath, NtroModel> localHeapByPath = new HashMap<>();

	public <M extends NtroModel> ModelLoader getLoaderImpl(Class<M> modelClass, String authToken, String firstPathName, String... pathRemainder){
		T.call(this);

		ModelLoader modelLoader = new ModelLoader(this);
		
		String documentId = firstPathName;
		for(String additionalSegment : pathRemainder) {
			documentId += "__" + additionalSegment;
		}
		
		DocumentPath documentPath = new DocumentPath();

		documentPath.setCollection(Ntro.introspector().getSimpleNameForClass(modelClass));
		documentPath.setDocumentId(documentId);
		
		JsonLoader jsonLoader = getJsonLoader(documentPath);
		jsonLoader.setTaskId("JsonLoader");

		modelLoader.setTargetClass(modelClass);

		//modelLoader.addPreviousTask(jsonLoader);
		modelLoader.addSubTask(jsonLoader);

		return modelLoader;
	}
	
	public static String emptyModelString(DocumentPath documentPath) {
		return "{\""+Constants.JSON_CLASS_KEY+"\":\""+documentPath.getCollection()+"\"}";
	}

	public abstract void addValueListener(ValuePath valuePath, ValueListener valueListener);

	// XXX: value could be a JsonObjectIO or a plain Java value
	public abstract <V extends Object> void setValue(ValuePath valuePath, V value);


	protected abstract void installExternalUpdateListener(ExternalUpdateListener updateListener);

	protected abstract JsonLoader getJsonLoader(DocumentPath documentPath);

	public abstract void saveJsonString(DocumentPath documentPath, String jsonString);

	public abstract void close();

	public void registerModel(DocumentPath documentPath, NtroModel ntroModel) {
		localHeap.put(ntroModel, documentPath);
		localHeapByPath.put(documentPath, ntroModel);
	}

	public void invokeValueMethod(ValuePath valuePath, String methodName, List<Object> args) {
		if(valuePath == null) return;

		DocumentPath documentPath = valuePath.getDocumentPath();

		NtroModel model = localHeapByPath.get(documentPath);
		MustNot.beNull(model);

		if(model != null) {

			
			Object value = Ntro.introspector().findByValuePath(model, valuePath);

			System.out.println("invokeValueMethod " + valuePath + " " + value);
			
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

		saveJsonString(documentPath, Ntro.jsonService().toString(model));
	}
}
