package ca.ntro.web;

import ca.ntro.core.mvc.NtroView;
import ca.ntro.web.mvc.NtroViewWeb;

public interface ViewRegistrarWeb {

	void registerView(Class<? extends NtroView> viewClass, 
		          	  String lang, 
		          	  String htmlPath, 
		          	  String cssPath, 
		          	  String stringsPath,
			          Class<? extends NtroViewWeb> webViewClass);

}
