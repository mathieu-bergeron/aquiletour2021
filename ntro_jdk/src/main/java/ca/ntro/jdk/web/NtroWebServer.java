package ca.ntro.jdk.web;

import ca.ntro.NtroBackend;
import ca.ntro.core.Constants;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jdk.services.BackendServiceServer;
import ca.ntro.jdk.services.EarlyInitializationJdk;
import ca.ntro.services.ModelStore;
import ca.ntro.services.NtroInitializationTask;
import ca.ntro.web.NtroAppWeb;

public class NtroWebServer {

	public static void launchServer(Class<? extends NtroBackend> backendClass, 
			                           Class<? extends NtroAppWeb> appClass, 
			                           String[] args) {

	}

	public static NtroInitializationTask defaultInitializationTask(Class<? extends BackendServiceServer> backendServiceClass, 
			                                                       Class<? extends ModelStore> modelStoreClass) {
		__T.call(NtroWebServer.class, "defaultInitializationTask");

		EarlyInitializationJdk earlyInitialization = new EarlyInitializationJdk();
		earlyInitialization.performInitialization();
		
		NtroInitializationTask initializationTask = new NtroInitializationTask();
		initializationTask.setTaskId(Constants.INITIALIZATION_TASK_ID);

		initializationTask.addSubTask(new InitializationTaskWebserver(backendServiceClass, modelStoreClass));

		return initializationTask;
	}

}
