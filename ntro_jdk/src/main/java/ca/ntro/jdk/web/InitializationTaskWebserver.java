package ca.ntro.jdk.web;

import ca.ntro.core.system.trace.__T;
import ca.ntro.jdk.services.BackendServiceServer;
import ca.ntro.jdk.services.InitializationTaskJdk;
import ca.ntro.services.BackendService;
import ca.ntro.services.ModelStore;
import ca.ntro.services.Ntro;

public class InitializationTaskWebserver extends InitializationTaskJdk {
	
	private final Class<? extends BackendServiceServer> backendServiceClass;
	private final Class<? extends ModelStore> modelStoreClass;

	public InitializationTaskWebserver(Class<? extends BackendServiceServer> backendServiceClass, Class<? extends ModelStore> modelStoreClass) {
		this.backendServiceClass = backendServiceClass;
		this.modelStoreClass = modelStoreClass;
	}

	@Override
	protected Class<? extends BackendService> provideBackendServiceClass() {
		__T.call(InitializationTaskWebserver.class, "provideBackendService");
		
		return backendServiceClass;
	}

	@Override
	protected ModelStore provideModelStore() {
		return Ntro.factory().newInstance(modelStoreClass);
	}
}