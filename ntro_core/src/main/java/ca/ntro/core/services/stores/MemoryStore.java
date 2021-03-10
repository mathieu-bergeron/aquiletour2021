package ca.ntro.core.services.stores;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.NtroUser;
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
	private Map<DocumentPath, String> values = NtroCollections.concurrentMap(new HashMap<DocumentPath, String>());
	private Map<String, String> valuesById = NtroCollections.concurrentMap(new HashMap<String, String>());
	
	private static MemoryStore instance = new MemoryStore();
	
	public static void reInitialize() {
		T.call(MemoryStore.class);

		instance = new MemoryStore();
	}

	// XXX: must synchronize here as get can be called from multiple threads
	public synchronized static <M extends NtroModel> ModelLoader getLoader(Class<M> modelClass, String firstPathName, String... pathRemainder) {
		T.call(MemoryStore.class);

		ModelLoader result = instance.getLoaderImpl(modelClass, "NO_TOKEN", firstPathName, pathRemainder);
		
		return result;
	}

	public synchronized static void clearStore() {
		T.call(MemoryStore.class);

		instance.values.clear();
	}

	@Override
	protected JsonLoader getJsonLoader(DocumentPath documentPath) {
		T.call(this);
		
		//JsonObject result = values.get(documentPath);
		String result = valuesById.get(documentPath.getDocumentId());
		
		// XXX: creates a new JsonObject when null
		if(result == null) {
			
			T.here();
			
			result = "{}";

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
		
		String document = values.get(valuePath.extractDocumentPath());
		
		//valuePath.updateObject(document, value);
	}

	@Override
	public void saveJsonString(DocumentPath documentPath, String jsonString) {
		T.call(this);
		values.put(documentPath, jsonString);
		valuesById.put(documentPath.getDocumentId(), jsonString);
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

	@Override
	public void registerThatUserObservesModel(NtroUser user, NtroModel model) {
		// XXX: nothing to do here.
	}
}
