package ca.ntro.jdk.web;

import ca.ntro.core.Constants;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jdk.services.BackendServiceServer;
import ca.ntro.services.EarlyInitialization;
import ca.ntro.services.MessageService;
import ca.ntro.services.ModelStore;
import ca.ntro.services.NtroInitializationTask;
import ca.ntro.services.RouterService;

public class NtroWebServer {

	public static NtroInitializationTask defaultInitializationTask(EarlyInitialization earlyInitialization,
																   Class<? extends BackendServiceServer> backendServiceClass, 
			                                                       Class<? extends ModelStore> modelStoreClass,
			                                                       Class<? extends MessageService> messageServiceClass,
			                                                       RouterService routerService) {
		__T.call(NtroWebServer.class, "defaultInitializationTask");

		earlyInitialization.performInitialization();

		
		NtroInitializationTask initializationTask = new NtroInitializationTask();
		initializationTask.setTaskId(Constants.INITIALIZATION_TASK_ID);

		initializationTask.addSubTask(new InitializationTaskWebserver(backendServiceClass, 
				                                                      modelStoreClass, 
				                                                      messageServiceClass, 
				                                                      routerService));
		return initializationTask;
	}

}
