package ca.ntro.jdk.web;

import ca.ntro.core.Constants;
import ca.ntro.core.initialization.NtroInitializationTask;
import ca.ntro.core.services.BackendService;
import ca.ntro.core.services.NtroCollections;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jdk.services.NtroCollectionsJdk;

public class NtroWebserver {

	public static NtroInitializationTask defaultInitializationTask(BackendService backendService) {
		__T.call(NtroWebserver.class, "defaultInitializationTask");

		NtroCollections.initialize(new NtroCollectionsJdk());
		
		NtroInitializationTask initializationTask = new NtroInitializationTask();
		initializationTask.setTaskId(Constants.INITIALIZATION_TASK_ID);

		initializationTask.addSubTask(new InitializationTaskWebserver(backendService));

		return initializationTask;
	}

}
