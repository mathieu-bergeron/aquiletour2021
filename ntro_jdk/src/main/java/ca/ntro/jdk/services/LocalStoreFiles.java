package ca.ntro.jdk.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.json.JsonLoaderMemory;
import ca.ntro.core.json.JsonObject;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.listeners.ValueListener;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.json.JsonLoaderFiles;
import ca.ntro.services.ModelStore;
import ca.ntro.stores.DocumentPath;
import ca.ntro.stores.ExternalUpdateListener;
import ca.ntro.stores.ValuePath;
import ca.ntro.users.NtroUser;

public class LocalStoreFiles extends ModelStore {
	
	protected String getFileName() {
		T.call(this);
		return "__data__"; // FIXME: DEV ONLY
	}

	private File rootDir = new File(getFileName());
	
	public LocalStoreFiles() {
		super();
		
		if(!rootDir.exists()) {
			rootDir.mkdir();
		}
	}

	@Override
	public void close() {
		T.call(this);
	}
	
	@Override
	public JsonLoader getJsonLoader(DocumentPath documentPath) {
		T.call(this);
		
		File modelFile = getModelFile(documentPath);
		
		if(!modelFile.exists()) {
			// XXX: create empty model if none exists
			writeJsonFile(modelFile,ModelStore.emptyModelString(documentPath));
		}
		
		JsonLoader jsonLoader = new JsonLoaderFiles(documentPath, modelFile);
		
		return jsonLoader;
	}

	private File getModelFile(DocumentPath documentPath) {
		T.call(this);
		
		Path collectionDirPath = Paths.get(rootDir.getAbsolutePath(), documentPath.getCollection());

		File collectionDir = collectionDirPath.toFile();
		
		if(!collectionDir.exists()) {
			collectionDir.mkdir();
		}
		
		Path modelFilePath = Paths.get(collectionDir.getAbsolutePath(), documentPath.getDocumentId() + ".json");
		
		File modelFile = modelFilePath.toFile();
		return modelFile;
	}

	private void writeJsonFile(File file, String jsonString) {
		T.call(this);

		try {

			FileOutputStream out = new FileOutputStream(file);
			out.write(jsonString.getBytes());
			out.close();

		} catch (IOException e) {
			Log.fatalError("Cannot write " + file.toString(), e);
		}
	}


	@Override
	public void saveJsonString(DocumentPath documentPath, String jsonString) {
		T.call(this);

		File modelFile = getModelFile(documentPath);
		
		writeJsonFile(modelFile, jsonString);
	}

	@Override
	protected boolean ifModelExists(DocumentPath documentPath) {
		return getModelFile(documentPath).exists();
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
	public void registerThatUserObservesModel(NtroUser user, DocumentPath documentPath, NtroModel model) {
		// XXX: must be overriden on the server
	}

	@Override
	public void onValueMethodInvoked(ValuePath valuePath, String methodName, List<Object> args) {
		// XXX: must be overriden on the server
	}

}
