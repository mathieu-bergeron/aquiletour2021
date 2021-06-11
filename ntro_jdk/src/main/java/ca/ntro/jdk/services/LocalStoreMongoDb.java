package ca.ntro.jdk.services;

import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;

import static com.mongodb.client.model.Filters.eq;

import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.json.JsonLoaderMemory;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.listeners.ValueListener;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroModelMessage;
import ca.ntro.services.ModelStore;
import ca.ntro.stores.DocumentPath;
import ca.ntro.stores.ExternalUpdateListener;
import ca.ntro.stores.ValuePath;

public abstract class LocalStoreMongoDb extends ModelStore {
	
	protected abstract String connectionString();
	protected abstract String databaseName();
	
	private MongoClient client = new MongoClient(connectionString());
	private MongoDatabase db = client.getDatabase(databaseName());
	
	public LocalStoreMongoDb() {
		super();
	}

	@Override
	public void close() {
		T.call(this);

		client.close();
	}

	@Override
	protected boolean ifModelExistsImpl(DocumentPath documentPath) {

		MongoCollection<Document> models = db.getCollection(documentPath.getCollection());
		
		FindIterable<Document> cursor = models.find(eq(ModelStore.MODEL_ID_KEY, documentPath.getDocumentId()));
		Document document  = cursor.first();
		
		return document != null;
	}
	
	@Override
	public JsonLoader getJsonLoader(Class<? extends NtroModel> targetClass, DocumentPath documentPath) {
		T.call(this);
		
		MongoCollection<Document> models = getOrCreateCollection(documentPath);

		FindIterable<Document> cursor = models.find(eq(ModelStore.MODEL_ID_KEY, documentPath.getDocumentId()));
		Document document  = cursor.first();
		
		String jsonString = null;
		
		if(document != null) {

			jsonString = (String) document.get(ModelStore.MODEL_DATA_KEY);

		}else {

			// XXX: create document if none exists
			jsonString = ModelStore.emptyModelString(documentPath);

			document = new Document();
			document.put(ModelStore.MODEL_ID_KEY, documentPath.getDocumentId());
			document.put(ModelStore.MODEL_DATA_KEY, jsonString);
			
			models.insertOne(document);
		}
		
		JsonLoader jsonLoader = new JsonLoaderMemory(jsonString);
		
		return jsonLoader;
	}

	private MongoCollection<Document> getOrCreateCollection(DocumentPath documentPath) {
		T.call(this);

		boolean ifExists = ifCollectionExists(documentPath);

		MongoCollection<Document> models = db.getCollection(documentPath.getCollection());
		
		if(!ifExists) {
			Log.info("[getOrCreateCollection] creating " + documentPath.getCollection());
			models.createIndex(Indexes.hashed(ModelStore.MODEL_ID_KEY));
		}
		return models;
	}
	
	private boolean ifCollectionExists(DocumentPath documentPath) {
		T.call(this);
		
		boolean ifExists = false;
		
		for(String candidateCollection : db.listCollectionNames()) {
			if(candidateCollection.equals(documentPath.getCollection())) {
				ifExists = true;
				break;
			}
		}

		return ifExists;
	}


	@Override
	public void saveDocument(DocumentPath documentPath, String jsonString) {
		T.call(this);

		MongoCollection<Document> models = db.getCollection(documentPath.getCollection());
		
		Document document = new Document();
		
		document.put(ModelStore.MODEL_ID_KEY, documentPath.getDocumentId());
		document.put(ModelStore.MODEL_DATA_KEY, jsonString);
		
		models.updateOne(eq(ModelStore.MODEL_ID_KEY, documentPath.getDocumentId()), document);
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
	public void onValueMethodInvoked(ValuePath valuePath, String methodName, List<Object> args) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void deleteDocument(DocumentPath documentPath) {
		throw new RuntimeException("TODO");
	}

	@Override
	protected JsonLoader jsonLoaderFromRequest(String serviceUrl, NtroModelMessage message) {
		// XXX: not supported
		return null;
	}

	@Override
	protected int maxHeapSize() {
		T.call(this);

		// DEV
		return 3;
	}

}
