package ca.ntro.jdk.services;



import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteCollection;

import static org.dizitart.no2.filters.Filters.eq;

import org.dizitart.no2.Cursor;

import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.json.JsonLoaderMemory;
import ca.ntro.core.json.JsonObject;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.models.ModelStore;
import ca.ntro.core.models.ModelStoreAsync;
import ca.ntro.core.models.stores.DocumentPath;
import ca.ntro.core.system.trace.T;

public abstract class LocalStore extends ModelStoreAsync {
	
	protected abstract String getFileName();
	private Nitrite db = Nitrite.builder().filePath(getFileName()).openOrCreate();
	
	public LocalStore() {
		super();
	}

	@Override
	public void close() {
		T.call(this);

		db.close();
	}
	
	@Override
	public JsonLoader getJsonObject(DocumentPath documentPath) {
		T.call(this);
		
		NitriteCollection models = db.getCollection(documentPath.getCollection());
		
		addIndex(models);
		
		Cursor cursor = models.find(eq(ModelStore.MODEL_ID_KEY, documentPath.getId()));
		Document document  = cursor.firstOrDefault();
		
		JsonObject jsonObject = null;
		
		if(document != null) {

			jsonObject = (JsonObject) document.get(ModelStore.MODEL_DATA_KEY);
			
			//System.out.println(jsonObject.toString());

		}else {

			// XXX: create document if none exists
			jsonObject = JsonParser.jsonObject();

			document = new Document();
			document.put(ModelStore.MODEL_ID_KEY, documentPath.getId());
			document.put(ModelStore.MODEL_DATA_KEY, jsonObject);

			models.insert(document);
		}
		
		JsonLoader jsonLoader = new JsonLoaderMemory(documentPath, jsonObject);
		
		return jsonLoader;
	}


	@Override
	protected void saveJsonObject(DocumentPath documentPath, JsonObject jsonObject) {
		T.call(this);

		NitriteCollection models = db.getCollection(documentPath.getCollection());
		
		Document document = new Document();
		
		document.put(ModelStore.MODEL_ID_KEY, documentPath.getId());
		document.put(ModelStore.MODEL_DATA_KEY, jsonObject);
		
		
		models.update(eq(ModelStore.MODEL_ID_KEY, documentPath.getId()), document);
		
		db.commit();
	}
	
	private void addIndex(NitriteCollection models) {
		T.call(this);
		
		// FÌXME: adding index freezes when the index already exists
		
		/*
		
		try {

			models.createIndex(ModelStore.MODEL_ID_KEY, indexOptions(IndexType.Unique));

		}catch(IndexingException e) {}
		*/
	}
	

}
