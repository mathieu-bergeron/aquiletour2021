package ca.aquiletour.web;

import java.util.Map;

import ca.ntro.core.tasks.NtroTask;

public interface RequestHandlerTask extends NtroTask {

	void writeHtml(StringBuilder out);
	void initialRequest(String path, 
			            Map<String, String[]> parameters, 
			            String authToken);

	void newRequest(String oldPath, 
			        String path, 
			        Map<String, String[]> oldParameters, 
			        Map<String, String[]> parameters, 
			        String authToken);
}
