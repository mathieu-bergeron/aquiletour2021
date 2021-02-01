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
import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.services.stores.DocumentPath;
import ca.ntro.core.services.stores.ExternalUpdateListener;
import ca.ntro.core.services.stores.ValuePath;
import ca.ntro.core.system.trace.T;

public class LocalStoreNitrite extends ModelStore {
	
	protected String getFileName() {
		T.call(this);
		return "DEV.db"; // FIXME: DEV ONLY
	}
	private Nitrite db = Nitrite.builder().filePath(getFileName()).openOrCreate();
	
	
	public LocalStoreNitrite() {
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

	@Override
	public void addValueListener(ValuePath valuePath, ValueListener valueListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <V> void setValue(ValuePath valuePath, V value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void installExternalUpdateListener(ExternalUpdateListener updateListener) {
		// TODO Auto-generated method stub
		
	}
	

}