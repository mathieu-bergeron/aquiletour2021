package ca.aquiletour.jsweet;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.web.AquiletourRequestHandler;
import ca.aquiletour.web.ViewLoaderRegistrationWeb;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.system.trace.T;
import ca.ntro.jsweet.services.NtroWindowJSweet;
import def.dom.PopStateEvent;

import static def.dom.Globals.window;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public class AquiletourMainJSweet extends AquiletourMain {
	
	public static void callRequestHandler(Map<String, String[]> parameters) {
		T.call(AquiletourMainJSweet.class);
		
		String rawPath = window.location.pathname;
		String rawParameters = window.location.search;
		
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
		
		AquiletourRequestHandler.sendMessages(AquiletourMain.createNtroContext(), path, parameters);
	}

	@Override
	protected void runTask() {
		T.call(this);

		super.runTask();
		
		callRequestHandler(new HashMap<>());
		
		window.onpopstate = new Function<PopStateEvent, Object>() {
			@Override
			public Object apply(PopStateEvent t) {
				T.call(this);
				
				callRequestHandler(new HashMap<>());

				return null;
			}
		};
	}

	@Override
	protected void registerViewLoaders() {
		T.call(this);
		
		ViewLoaderRegistrationWeb.registerViewLoaders();
	}

	@Override
	protected NtroWindow getWindow() {
		T.call(this);

		return new NtroWindowJSweet();
	}
}
