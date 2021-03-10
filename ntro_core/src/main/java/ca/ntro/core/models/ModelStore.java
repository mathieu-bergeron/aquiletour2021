package ca.ntro.core.models;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.core.Ntro;
import ca.ntro.core.NtroUser;
import ca.ntro.core.introspection.NtroClass;
import ca.ntro.core.introspection.NtroMethod;
import ca.ntro.core.json.Constants;
import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.services.stores.DocumentPath;
import ca.ntro.core.services.stores.ExternalUpdateListener;
import ca.ntro.core.services.stores.ValuePath;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.ntro_messages.UpdateModelNtroMessage;

public abstract class ModelStore {

	public static final String MODEL_ID_KEY="modelId";
	public static final String MODEL_DATA_KEY="modelData";
	
	private Map<String, NtroModel> registeredModels = new HashMap<>();

	public <M extends NtroModel> ModelLoader getLoaderImpl(Class<M> modelClass, String authToken, String firstPathName, String... pathRemainder){
		T.call(this);

		ModelLoader modelLoader = new ModelLoader(this);
		
		DocumentPath documentPath = new DocumentPath(modelClass.getSimpleName(), firstPathName, pathRemainder);
		
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
		registeredModels.put(documentPath.toString(), ntroModel);
	}

	public void callOnModel(ValuePath valuePath, String methodName, List<Object> arguments) {

		DocumentPath documentPath = valuePath.extractDocumentPath();
		
		NtroModel model = registeredModels.get(documentPath.toString());
		
		if(model != null) {
			
			Object modelValue = Ntro.introspector().findByValuePath(model, valuePath);
			NtroClass valueClass = Ntro.introspector().ntroClassFromObject(modelValue);
			NtroMethod methodToCall = valueClass.findMethodByName(methodName);
			try {
				methodToCall.invoke(modelValue, arguments);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Log.fatalError("Unable to invoke " + methodName + "on valuePath " + valuePath.toString(), e);
			}
		}
	}

	public abstract void registerThatUserObservesModel(NtroUser user, NtroModel model);

}
