package ca.ntro.jdk.json;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

public class JsonLoaderFiles extends JsonLoader {

	private File modelFile;
	private String jsonString;
	
	public JsonLoaderFiles(File modelFile) {
		super();
		T.call(this);
		
		this.modelFile = modelFile;
	}

	@Override
	public String getJsonString() {
		return jsonString;
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

		}

		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}
}
