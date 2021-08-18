package ca.ntro.jdk.services;

import java.util.List;
import java.util.function.Consumer;

import org.bson.Document;

import com.mongodb.InsertOptions;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.ReplaceOptions;

import static com.mongodb.client.model.Filters.eq;

import ca.ntro.backend.BackendError;
import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.json.JsonLoaderMemory;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.listeners.ValueListener;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.wrappers.options.EmptyOptionException;
import ca.ntro.core.wrappers.options.Optionnal;
import ca.ntro.messages.NtroModelMessage;
import ca.ntro.services.ModelIdReader;
import ca.ntro.services.ModelStore;
import ca.ntro.services.Ntro;
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
			
			Document model = document.get(ModelStore.MODEL_DATA_KEY, Document.class);

			jsonString = Ntro.jsonService().toString(model);

			jsonString = reInsertSpecialChars(jsonString);

		}else {

			// XXX: create document if none exists
			jsonString = ModelStore.emptyModelString(documentPath);

			document = createDocument(documentPath, jsonString);

			models.insertOne(document);
		}
		
		JsonLoader jsonLoader = new JsonLoaderMemory(jsonString);
		
		return jsonLoader;
	}

	private String reInsertSpecialChars(String jsonString) {
		T.call(this);

		jsonString = jsonString.replaceAll("\uFF0E", ".");
		jsonString = jsonString.replaceAll("\uFF04", "$");
		return jsonString;
	}

	private Document createDocument(DocumentPath documentPath, String jsonString) {
		T.call(this);

		Document document = new Document();
		
		jsonString = removeSpecialChars(jsonString);
		
		document.put(ModelStore.MODEL_ID_KEY, documentPath.getDocumentId());
		document.put(ModelStore.MODEL_DATA_KEY, Document.parse(jsonString));

		return document;
	}
	private String removeSpecialChars(String jsonString) {
		T.call(this);

		jsonString = jsonString.replaceAll("\\.", "\uFF0E");
		jsonString = jsonString.replaceAll("\\$", "\uFF04");
		return jsonString;
	}

	private MongoCollection<Document> getOrCreateCollection(DocumentPath documentPath) {
		T.call(this);

		return getOrCreateCollection(documentPath.getCollection());
	}

	private MongoCollection<Document> getOrCreateCollection(String collectionName) {
		T.call(this);

		boolean ifExists = ifCollectionExists(collectionName);

		MongoCollection<Document> models = db.getCollection(collectionName);
		
		if(!ifExists) {
			Log.info("[getOrCreateCollection] creating " + collectionName);
			models.createIndex(Indexes.hashed(ModelStore.MODEL_ID_KEY));
		}
		return models;
	}

	private boolean ifCollectionExists(String collectionName) {
		T.call(this);
		
		boolean ifExists = false;
		
		for(String candidateCollection : db.listCollectionNames()) {
			if(candidateCollection.equals(collectionName)) {
				ifExists = true;
				break;
			}
		}

		return ifExists;
	}

	@Override
	public void saveDocument(DocumentPath documentPath, String jsonString) {
		T.call(this);

		MongoCollection<Document> models = getOrCreateCollection(documentPath);
		
		Document document = createDocument(documentPath, jsonString);
		
		ReplaceOptions replaceOptions = new ReplaceOptions();
		replaceOptions.upsert(true);
		
		models.replaceOne(eq(ModelStore.MODEL_ID_KEY, documentPath.getDocumentId()), document, replaceOptions);

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
		T.call(this);

		MongoCollection<Document> models = db.getCollection(documentPath.getCollection());

		models.deleteOne(eq(ModelStore.MODEL_ID_KEY, documentPath.getDocumentId()));
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

	@Override
	protected void forEachDocumentIdImpl(String collectionName, ModelIdReader reader) throws BackendError {

		MongoCollection<Document> models = getOrCreateCollection(collectionName);

		FindIterable<Document> cursor = models.find();
		
		Optionnal<BackendError> backendError = new Optionnal<>();

		cursor.forEach(new Consumer<Document>() {
			@Override
			public void accept(Document t) {
				String modelId = t.getString(ModelStore.MODEL_ID_KEY);

				try {

					reader.onModelId(modelId);

				} catch (BackendError e) {
					backendError.set(e);
				}
			}
		});
		
		try {

			throw backendError.get();

		} catch (EmptyOptionException e) {}
	}

}
