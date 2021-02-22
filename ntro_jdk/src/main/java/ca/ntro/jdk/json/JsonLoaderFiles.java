package ca.ntro.jdk.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.json.JsonObject;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.services.stores.DocumentPath;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

public class JsonLoaderFiles extends JsonLoader {

	private DocumentPath documentPath;
	private File modelFile;
	private JsonObject jsonObject;
	
	public JsonLoaderFiles(DocumentPath documentPath, File modelFile) {
		super();
		T.call(this);
		
		this.documentPath = documentPath;
		this.modelFile = modelFile;
	}

	@Override
	public JsonObject getJsonObject() {
		return jsonObject;
	}

	@Override
	public DocumentPath getDocumentPath() {
		return documentPath;
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		T.values(documentPath.getCollection(), documentPath.getId());

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

		T.values(jsonObject.toString());
		
		notifyTaskFinished();
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
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
