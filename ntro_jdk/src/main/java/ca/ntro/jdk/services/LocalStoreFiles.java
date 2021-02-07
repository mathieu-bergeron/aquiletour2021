package ca.ntro.jdk.services;

import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteCollection;

import static org.dizitart.no2.filters.Filters.eq;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

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
	public JsonLoader getJsonObject(DocumentPath documentPath) {
		T.call(this);
		
		File modelFile = getModelFile(documentPath);
		
		JsonObject jsonObject = null;
		
		if(modelFile.exists()) {
			
			try {

				jsonObject = JsonParser.fromStream(new FileInputStream(modelFile));

			} catch (FileNotFoundException e) {
				
				Log.fatalError("Cannot load " + modelFile.toString(), e);
			}

		}else {

			// XXX: create document if none exists
			jsonObject = JsonParser.jsonObject();

			writeJsonFile(modelFile, jsonObject);
		}

		JsonLoader jsonLoader = new JsonLoaderMemory(documentPath, jsonObject);
		
		return jsonLoader;
	}

	private File getModelFile(DocumentPath documentPath) {
		T.call(this);
		
		Path collectionDirPath = Paths.get(rootDir.getAbsolutePath(), documentPath.getCollection());

		File collectionDir = collectionDirPath.toFile();
		
		if(!collectionDir.exists()) {
			collectionDir.mkdir();
		}
		
		Path modelFilePath = Paths.get(collectionDir.getAbsolutePath(), documentPath.getId() + ".json");
		
		File modelFile = modelFilePath.toFile();
		return modelFile;
	}

	private void writeJsonFile(File file, JsonObject jsonObject) {
		T.call(this);

		try {

			FileOutputStream out = new FileOutputStream(file);
			out.write(JsonParser.toString(jsonObject).getBytes());
			out.close();

		} catch (IOException e) {
			Log.fatalError("Cannot write " + file.toString(), e);
		}
	}


	@Override
	protected void saveJsonObject(DocumentPath documentPath, JsonObject jsonObject) {
		T.call(this);

		File modelFile = getModelFile(documentPath);
		
		writeJsonFile(modelFile, jsonObject);
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