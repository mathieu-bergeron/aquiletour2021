package ca.ntro.jdk.web;

import ca.ntro.core.initialization.NtroInitializationTask;
import ca.ntro.core.system.trace.__T;

public class NtroWebserver {

	public static NtroInitializationTask defaultInitializationTask(String indexHtmlPath) {
		__T.call(NtroWebserver.class, "defaultInitializationTask");
		
		NtroInitializationTask initializationTask = new NtroInitializationTask();
		initializationTask.addSubTask(new InitializationTaskWebserver(indexHtmlPath));

		return initializationTask;
	}

}
