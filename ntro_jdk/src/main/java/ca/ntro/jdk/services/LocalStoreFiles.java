package ca.ntro.jdk.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.json.JsonLoaderMemory;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.listeners.ValueListener;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.json.JsonLoaderFiles;
import ca.ntro.messages.NtroModelMessage;
import ca.ntro.services.ModelStore;
import ca.ntro.stores.DocumentPath;
import ca.ntro.stores.ExternalUpdateListener;
import ca.ntro.stores.ValuePath;

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
	public JsonLoader getJsonLoader(Class<? extends NtroModel> targetClass, DocumentPath documentPath) {
		T.call(this);
		
		File modelFile = getModelFile(documentPath);

		JsonLoader jsonLoader = null;
		
		if(modelFile.exists()) {

			jsonLoader = new JsonLoaderFiles(modelFile);

		}else {

			// Create empty model if non exists
			jsonLoader = new JsonLoaderMemory(ModelStore.emptyModelString(documentPath));
		}
		
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
			
			//System.out.println("Saved: " + file.getAbsolutePath());

		} catch (IOException e) {
			Log.fatalError("Cannot write " + file.toString(), e);
		}
	}

	@Override
	protected void deleteDocument(DocumentPath documentPath) {
		T.call(this);
		
		File modelFile = getModelFile(documentPath);
		
		modelFile.delete();
	}

	@Override
	public void saveDocument(DocumentPath documentPath, String jsonString) {
		T.call(this);

		File modelFile = getModelFile(documentPath);
		
		writeJsonFile(modelFile, jsonString);
	}

	@Override
	protected boolean ifModelExistsImpl(DocumentPath documentPath) {
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
	protected JsonLoader jsonLoaderFromRequest(String serviceUrl, NtroModelMessage message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onValueMethodInvoked(ValuePath valuePath, String methodName, List<Object> args) {
		// TODO Auto-generated method stub
		
	}



}
