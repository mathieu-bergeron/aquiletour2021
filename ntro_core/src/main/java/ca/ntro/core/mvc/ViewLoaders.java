package ca.ntro.core.mvc;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.system.trace.T;
import ca.ntro.web.mvc.NtroViewWeb;
import ca.ntro.web.mvc.ViewLoaderWeb;

public class ViewLoaders {
	
	private static class ViewLoaderId {
		
		private Class<? extends NtroView> viewClass;
		private String lang;
		
		public ViewLoaderId(Class<? extends NtroView> viewClass, String lang) {
			T.call(this);

			this.viewClass = viewClass;
			this.lang = lang;
		}
		
		@Override
		public int hashCode() {
			return viewClass.hashCode() + lang.hashCode();
		}
		
		@Override
		public boolean equals(Object other) {
			if(other instanceof ViewLoaderId) {
				
				ViewLoaderId otherId = (ViewLoaderId) other;
				
				return viewClass.equals(otherId.viewClass) 
						&& lang.equals(otherId.lang);
			}else {
				return false;
			}
		}
		
	}
	
	private static final Map<ViewLoaderId, ViewLoader> viewLoaders = new HashMap<>();
	
	public static void registerViewLoader(Class<? extends NtroView> viewClass, String lang, ViewLoader viewLoader) {
		T.call(ViewLoaders.class);
		
		viewLoaders.put(new ViewLoaderId(viewClass, lang), viewLoader);
	}
	
	public static ViewLoader getViewLoader(Class<? extends NtroView> viewClass, String lang) {
		T.call(ViewLoaders.class);
		
		ViewLoader viewLoader = viewLoaders.get(new ViewLoaderId(viewClass, lang));
		
		if(viewLoader == null) {
			System.out.println("[getViewLoader] null viewLoader: " + viewClass.getSimpleName() + " (lang: " + lang + ")");
		}
		
		return (ViewLoader) viewLoader.clone();
	}

	public static Class<? extends NtroViewWeb> getViewTargetClassWeb(Class<? extends NtroView> viewClass, String lang) {
		T.call(ViewLoaders.class);
		
		ViewLoader viewLoader = getViewLoader(viewClass, lang);
		
		Class<? extends NtroViewWeb> viewTargetClass = null;
		
		if(viewLoader != null
				&& viewLoader instanceof ViewLoaderWeb) {
			
			viewTargetClass = ((ViewLoaderWeb) viewLoader).getTargetClass();
			
		}

		return viewTargetClass;
	}
}
