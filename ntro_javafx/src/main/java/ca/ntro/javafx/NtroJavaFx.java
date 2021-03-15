package ca.ntro.javafx;

import ca.ntro.core.system.trace.__T;
import ca.ntro.jdk.services.CollectionsServiceJdk;
import ca.ntro.services.CollectionsService;
import ca.ntro.services.Ntro;
import ca.ntro.services.NtroInitializationTask;

public class NtroJavaFx {

	public static NtroInitializationTask defaultInitializationTask() {
		__T.call(NtroJavaFx.class, "defaultInitializationTask");
		
		// FIXME: collections must be initialized before creating a task
		Ntro.registerCollectionsService(new CollectionsServiceJdk());
		
		NtroInitializationTask initializationTask = new NtroInitializationTask();
		initializationTask.addSubTask(new InitializationTaskJavaFx());
		
		initializationTask.setTaskId("initializationTask");

		return initializationTask;
	}

}
