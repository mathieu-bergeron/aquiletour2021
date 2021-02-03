package ca.ntro.jdk.web;

import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.services.InitializationTaskJdk;
import ca.ntro.web.NtroWindowWeb;

public class InitializationTaskWebserver extends InitializationTaskJdk {
	
	NtroWindowWeb window;
	
	public InitializationTaskWebserver(String indexHtmlPath) {
		super();
		T.call(this);
		
		window = new NtroWindowServer(indexHtmlPath);
	}

	@Override
	protected NtroWindow provideWindow() {
		T.call(this);
		
		MustNot.beNull(window);
		
		return window;
	}

}
