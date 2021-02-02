package ca.ntro.core.mvc.view;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.system.trace.T;

public class ViewLoaders {
	
	private static class ViewLoaderId {
		
		private Class<? extends NtroView> viewClass;
		private String lang;
		
		public ViewLoaderId(Class<? extends NtroView> viewClass, String lang) {
			T.call(this);

			this.viewClass = viewClass;
			this.lang = lang;
		}
	}
	
	private static final Map<ViewLoaderId, ViewLoader> viewLoaders = new HashMap<>();
	
	public static void registerViewLoader(Class<? extends NtroView> viewClass, String lang, ViewLoader viewLoader) {
		T.call(ViewLoaders.class);
		
		viewLoaders.put(new ViewLoaderId(viewClass, lang), viewLoader);
	}
	
	public static ViewLoader getViewLoader(Class<? extends NtroView> viewClass, String lang) {
		T.call(ViewLoaders.class);
		
		return viewLoaders.get(new ViewLoaderId(viewClass, lang));
	}
}
