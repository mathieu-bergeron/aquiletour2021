package ca.ntro.jdk.services;

import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteCollection;

import static org.dizitart.no2.filters.Filters.eq;

import org.dizitart.no2.Cursor;

import ca.ntro.core.NtroUser;
import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.json.JsonLoaderMemory;
import ca.ntro.core.json.JsonObject;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.models.ModelStore;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.ValueListener;
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
	public JsonLoader getJsonLoader(DocumentPath documentPath) {
		T.call(this);
		
		NitriteCollection models = db.getCollection(documentPath.getCollection());
		
		addIndex(models);
		
		Cursor cursor = models.find(eq(ModelStore.MODEL_ID_KEY, documentPath.getDocumentId()));
		Document document  = cursor.firstOrDefault();
		
		String jsonString = null;
		
		if(document != null) {

			jsonString = (String) document.get(ModelStore.MODEL_DATA_KEY);
			
			//System.out.println(jsonObject.toString());

		}else {

			// XXX: create document if none exists
			jsonString = ModelStore.emptyModelString(documentPath);

			document = new Document();
			document.put(ModelStore.MODEL_ID_KEY, documentPath.getDocumentId());
			document.put(ModelStore.MODEL_DATA_KEY, jsonString);

			models.insert(document);
		}
		
		JsonLoader jsonLoader = new JsonLoaderMemory(documentPath, jsonString);
		
		return jsonLoader;
	}


	@Override
	public void saveJsonString(DocumentPath documentPath, String jsonString) {
		T.call(this);

		NitriteCollection models = db.getCollection(documentPath.getCollection());
		
		Document document = new Document();
		
		document.put(ModelStore.MODEL_ID_KEY, documentPath.getDocumentId());
		document.put(ModelStore.MODEL_DATA_KEY, jsonString);
		
		
		models.update(eq(ModelStore.MODEL_ID_KEY, documentPath.getDocumentId()), document);
		
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

	@Override
	public void registerThatUserObservesModel(NtroUser user, NtroModel model) {
		// TODO Auto-generated method stub
		
	}
	

}
