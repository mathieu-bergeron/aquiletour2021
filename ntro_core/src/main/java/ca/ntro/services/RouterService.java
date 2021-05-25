package ca.ntro.services;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.Path;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;

public abstract class RouterService {

	public void sendMessagesFor(NtroContext<?,?> context, String rawHref) {
		T.call(this);
		
		String[] segments = rawHref.split("?");
		
		String rawPath = segments[0];
		String rawParameters = "?" + segments[1];

		sendMessagesFor(context, rawPath, rawParameters);
	}
	
	public void sendMessagesFor(NtroContext<?,?> context, String rawPath, String rawParameters) {
		T.call(this);
		
		Map<String, String[]> parameters = new HashMap<>();
		
		Path path = new Path(rawPath);
		
		if(rawParameters.startsWith("?")) {
			rawParameters = rawParameters.substring(1);
			String[] segments = rawParameters.split("&");
			for(String segment : segments) {
				String[] nameValue = segment.split("=");
				String name = nameValue[0];
				String value = nameValue[1];

				parameters.put(name, new String[] {value});
			}
		}
		
		sendMessagesFor(context, path, parameters);
	}

	public void sendMessagesFor(NtroContext<?, ?> context, Path path, Map<String, String[]> parameters) {
		T.call(this);

		System.out.println("path: " + path.toString());
		System.out.println("parameters: ");
		for(Map.Entry<String, String[]> entry : parameters.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue()[0]);
		}
		
		sendBackendMessagesFor(context, path, parameters);
		sendFrontendMessagesFor(context, path, parameters);
	}

	public abstract void sendFrontendMessagesFor(NtroContext<?,?> context, Path path, Map<String, String[]> parameters);
	public abstract void sendBackendMessagesFor(NtroContext<?,?> context, Path path, Map<String, String[]> parameters);

}
