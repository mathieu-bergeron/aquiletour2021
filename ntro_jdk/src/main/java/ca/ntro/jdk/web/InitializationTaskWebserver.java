package ca.ntro.jdk.web;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jdk.services.BackendServiceServer;
import ca.ntro.jdk.services.InitializationTaskJdk;
import ca.ntro.services.BackendService;
import ca.ntro.services.ConfigService;
import ca.ntro.services.MessageService;
import ca.ntro.services.ModelStore;
import ca.ntro.services.Ntro;
import ca.ntro.services.RouterService;

public class InitializationTaskWebserver extends InitializationTaskJdk {
	
	private final Class<? extends BackendServiceServer> backendServiceClass;
	private final Class<? extends ModelStore> modelStoreClass;
	private final Class<? extends MessageService> messageServiceClass;
	private final RouterService routerService;

	public InitializationTaskWebserver(Class<? extends BackendServiceServer> backendServiceClass, 
			                           Class<? extends ModelStore> modelStoreClass, 
			                           Class<? extends MessageService> messageServiceClass, 
			                           RouterService routerService) {
		super();
		T.call(this);

		this.backendServiceClass = backendServiceClass;
		this.modelStoreClass = modelStoreClass;
		this.messageServiceClass = messageServiceClass;
		this.routerService = routerService;
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

	@Override
	protected Class<? extends MessageService> provideMessageServiceClass() {
		__T.call(InitializationTaskWebserver.class, "provideMessageServiceClass");
		
		return messageServiceClass;
	}

	@Override
	protected RouterService provideRouterService() {
		return routerService;
	}
}