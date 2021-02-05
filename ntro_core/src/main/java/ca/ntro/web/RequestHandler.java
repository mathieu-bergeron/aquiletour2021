package ca.ntro.web;

import java.util.Map;

import ca.ntro.core.Path;

public interface RequestHandler {

	void initialRequest(Path path, 
			            Map<String, String[]> parameters, 
			            String authToken);

	void newRequest(Path oldPath, 
			        Path path, 
			        Map<String, String[]> oldParameters, 
			        Map<String, String[]> parameters, 
			        String authToken);

}
