package ca.ntro.jdk.web;

import ca.ntro.core.introspection.Factory;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jdk.services.BackendServiceServer;
import ca.ntro.jdk.services.InitializationTaskJdk;
import ca.ntro.services.BackendService;
import ca.ntro.services.ModelStore;

public class InitializationTaskWebserver extends InitializationTaskJdk {
	
	private final Class<? extends BackendServiceServer> backendServiceClass;
	private final ModelStore localStore;

	public InitializationTaskWebserver(Class<? extends BackendServiceServer> backendServiceClass, ModelStore localStore) {
		this.backendServiceClass = backendServiceClass;
		this.localStore = localStore;
	}

	@Override
	protected BackendService provideBackendService() {
		__T.call(InitializationTaskWebserver.class, "provideBackendService");
		
		BackendServiceServer service = Factory.newInstance(backendServiceClass);
		
		service.setModelStore(localStore);
		
		return service;
		
	}
}
