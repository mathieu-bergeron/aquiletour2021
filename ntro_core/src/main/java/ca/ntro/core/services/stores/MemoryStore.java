package ca.ntro.core.services.stores;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.json.JsonLoaderMemory;
import ca.ntro.core.json.JsonObject;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.ModelStore;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.services.NtroCollections;
import ca.ntro.core.system.trace.T;

public class MemoryStore extends ModelStore {
	
	// XXX: we need ConcurrentHashMap as MemoryStore is accessed from different threads
	private Map<DocumentPath, JsonObject> values = NtroCollections.concurrentHashMap(new HashMap<DocumentPath, JsonObject>());
	private Map<String, JsonObject> valuesById = NtroCollections.concurrentHashMap(new HashMap<String, JsonObject>());
	
	private static MemoryStore instance = new MemoryStore();
	
	public static void reInitialize() {
		T.call(MemoryStore.class);

		instance = new MemoryStore();
	}

	// XXX: must synchronize here as get can be called from multiple threads
	public synchronized static <M extends NtroModel> ModelLoader getLoader(Class<M> modelClass, String modelId) {
		T.call(MemoryStore.class);

		ModelLoader result = instance.getLoaderImpl(modelClass, modelId);
		
		return result;
	}

	public synchronized static void clearStore() {
		T.call(MemoryStore.class);

		instance.values.clear();
	}

	@Override
	protected JsonLoader getJsonObject(DocumentPath documentPath) {
		T.call(this);
		
		//JsonObject result = values.get(documentPath);
		JsonObject result = valuesById.get(documentPath.getId());
		
		// XXX: creates a new JsonObject when null
		if(result == null) {
			
			T.here();
			
			result = JsonParser.jsonObject();

			values.put(documentPath, result);
		}
		
		return new JsonLoaderMemory(documentPath, result);
	}

	@Override
	public void addValueListener(ValuePath valuePath, ValueListener valueListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <V extends Object> void setValue(ValuePath valuePath, V value) {
		T.call(this);
		
		JsonObject document = values.get(valuePath.extractDocumentPath());
		
		valuePath.updateObject(document, value);
	}

	@Override
	protected void saveJsonObject(DocumentPath documentPath, JsonObject jsonObject) {
		T.call(this);
		values.put(documentPath, jsonObject);
		valuesById.put(documentPath.getId(), jsonObject);
		T.values(jsonObject.toString());
	}

	@Override
	public void close() {
		T.call(this);
		
	}

	@Override
	protected void installExternalUpdateListener(ExternalUpdateListener updateListener) {
		T.call(this);
		
		// XXX: nothing to do here.
		// XXX: this is when some other program can modify the store
		// XXX: for now, probably only for HTML5 LocalStorage
	}
}
