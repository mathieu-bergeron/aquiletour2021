package ca.ntro.jdk.web;

import ca.ntro.core.Constants;
import ca.ntro.core.initialization.NtroInitializationTask;
import ca.ntro.core.models.ModelStore;
import ca.ntro.core.services.NtroCollections;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jdk.services.BackendServiceServer;
import ca.ntro.jdk.services.NtroCollectionsJdk;

public class NtroWebserver {

	public static NtroInitializationTask defaultInitializationTask(Class<? extends BackendServiceServer> backendServiceClass, ModelStore localStore) {
		__T.call(NtroWebserver.class, "defaultInitializationTask");

		NtroCollections.initialize(new NtroCollectionsJdk());
		
		NtroInitializationTask initializationTask = new NtroInitializationTask();
		initializationTask.setTaskId(Constants.INITIALIZATION_TASK_ID);

		initializationTask.addSubTask(new InitializationTaskWebserver(backendServiceClass, localStore));

		return initializationTask;
	}

}
