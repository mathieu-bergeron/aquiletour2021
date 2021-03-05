package ca.ntro.jdk.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.json.JsonObject;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.services.stores.DocumentPath;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

public class JsonLoaderFiles extends JsonLoader {

	private DocumentPath documentPath;
	private File modelFile;
	private String jsonString;
	
	public JsonLoaderFiles(DocumentPath documentPath, File modelFile) {
		super();
		T.call(this);
		
		this.documentPath = documentPath;
		this.modelFile = modelFile;
	}

	@Override
	public String getJsonString() {
		return jsonString;
	}

	@Override
	public DocumentPath getDocumentPath() {
		return documentPath;
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);

		if(modelFile.exists()) {
			
			try {
				
				List<String> lines = Files.readAllLines(modelFile.toPath());
				
				StringBuilder builder = new StringBuilder();
				lines.forEach(l -> builder.append(l));

				jsonString = builder.toString();

			} catch (IOException e) {
				
				Log.fatalError("Cannot load " + modelFile.toString(), e);
			}

		}else {

			// XXX: create document if none exists
			jsonString = "{}";

			writeJsonFile(modelFile, jsonString);
		}

		notifyTaskFinished();
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
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
