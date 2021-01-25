package ca.aquiletour.web;

import java.util.Map;

import ca.ntro.core.system.trace.T;

public class AquiletourRequestHandler {
	
	public HandlerTask initialRequest(String path, 
			                          Map<String, String[]> parameters, 
			                          String authToken) {
		T.call(this);

		// TODO: 
		// load index.html (the Document)
		// create MVC for RootPage
		//       send messages according to path
		//       (this creates the document)
		
		return new HandlerTask();
	}

	public HandlerTask newRequest(String oldPath, 
			               String path, 
			               Map<String, String[]> oldParameters, 
			               Map<String, String[]> parameters, 
			               String authToken) {
		T.call(this);
		
		// TODO: this should only be called in JS
		//       we send appropriate messages to the rootpage controller

		// TODO
		return new HandlerTask();
	}

	

}
