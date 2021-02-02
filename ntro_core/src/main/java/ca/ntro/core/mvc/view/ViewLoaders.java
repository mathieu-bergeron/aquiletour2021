package ca.ntro.core.mvc.view;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.system.trace.T;

public class ViewLoaders {
	
	private static final Map<Class<? extends NtroView>, ViewLoader> viewLoaders = new HashMap<>();
	
	public static void registerViewLoader(Class<? extends NtroView> viewClass, ViewLoader viewLoader) {
		T.call(ViewLoaders.class);
		
		viewLoaders.put(viewClass, viewLoader);
	}

}
