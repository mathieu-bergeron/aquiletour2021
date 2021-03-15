package ca.ntro.jdk.web;

import ca.ntro.core.Constants;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jdk.services.BackendServiceServer;
import ca.ntro.jdk.services.EarlyInitializationJdk;
import ca.ntro.services.ModelStore;
import ca.ntro.services.NtroInitializationTask;

public class NtroWebserver {

	public static NtroInitializationTask defaultInitializationTask(Class<? extends BackendServiceServer> backendServiceClass, ModelStore localStore) {
		__T.call(NtroWebserver.class, "defaultInitializationTask");

		EarlyInitializationJdk earlyInitialization = new EarlyInitializationJdk();
		earlyInitialization.performInitialization();
		
		NtroInitializationTask initializationTask = new NtroInitializationTask();
		initializationTask.setTaskId(Constants.INITIALIZATION_TASK_ID);

		initializationTask.addSubTask(new InitializationTaskWebserver(backendServiceClass, localStore));

		return initializationTask;
	}

}
