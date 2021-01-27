package ca.aquiletour.web;

import java.util.Map;

import ca.ntro.core.tasks.NtroTask;

public interface RequestHandlerTask extends NtroTask {

	void initialRequest(Path path, 
			            Map<String, String[]> parameters, 
			            String authToken);

	void newRequest(Path oldPath, 
			        Path path, 
			        Map<String, String[]> oldParameters, 
			        Map<String, String[]> parameters, 
			        String authToken);

}
