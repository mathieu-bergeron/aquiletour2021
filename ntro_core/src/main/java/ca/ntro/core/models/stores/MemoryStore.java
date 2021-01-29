package ca.ntro.core.models.stores;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.json.JsonLoaderMemory;
import ca.ntro.core.json.JsonObject;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.ModelStoreAsync;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.services.NtroCollections;
import ca.ntro.core.system.trace.T;

public class MemoryStore extends ModelStoreAsync {
	
	// XXX: we need ConcurrentHashMap as MemoryStore is accessed from different threads
	private Map<DocumentPath, JsonObject> values = NtroCollections.concurrentHashMap(new HashMap<DocumentPath, JsonObject>());
	
	private static MemoryStore instance = new MemoryStore();

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
		
		JsonObject result = values.get(documentPath);
		
		// XXX: creates a new JsonObject when null
		if(result == null) {
			
			result = JsonParser.jsonObject();

			values.put(documentPath, result);
		}
		
		return new JsonLoaderMemory(result);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void saveJsonObject(JsonObject jsonObject, DocumentPath documentPath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		T.call(this);
		
	}

}
