package ca.ntro.javafx;

import ca.ntro.core.initialization.NtroInitializationTask;
import ca.ntro.core.system.trace.__T;

public class NtroJavaFx {

	public static NtroInitializationTask defaultInitializationTask() {
		__T.call(NtroJavaFx.class, "defaultInitializationTask");
		
		NtroInitializationTask initializationTask = new NtroInitializationTask();
		initializationTask.addSubTask(new InitializationTaskJavaFx());

		return initializationTask;
	}

}
