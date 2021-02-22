package ca.ntro.javafx;

import ca.ntro.core.initialization.NtroInitializationTask;
import ca.ntro.core.services.NtroCollections;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jdk.services.NtroCollectionsJdk;

public class NtroJavaFx {

	public static NtroInitializationTask defaultInitializationTask() {
		__T.call(NtroJavaFx.class, "defaultInitializationTask");
		
		NtroCollections.initialize(new NtroCollectionsJdk());
		
		NtroInitializationTask initializationTask = new NtroInitializationTask();
		initializationTask.addSubTask(new InitializationTaskJavaFx());
		
		initializationTask.setTaskId("initializationTask");

		return initializationTask;
	}

}
