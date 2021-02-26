package ca.ntro.jdk.web;

import ca.ntro.core.services.BackendService;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jdk.services.InitializationTaskJdk;

public class InitializationTaskWebserver extends InitializationTaskJdk {
	
	private BackendService backendService;

	public InitializationTaskWebserver(BackendService backendService) {
		this.backendService = backendService;
	}

	@Override
	protected BackendService provideBackendService() {
		__T.call(InitializationTaskWebserver.class, "provideBackendService");
		
		return backendService;
	}
}
