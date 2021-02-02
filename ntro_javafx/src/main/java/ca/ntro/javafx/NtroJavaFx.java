package ca.ntro.javafx;

import ca.ntro.core.initialization.NtroInitializationTask;
import ca.ntro.core.system.trace.__T;
import javafx.stage.Stage;

public class NtroJavaFx {

	public static NtroInitializationTask defaultInitializationTask(Stage primaryStage) {
		__T.call(NtroJavaFx.class, "defaultInitializationTask");
		
		NtroInitializationTask initializationTask = new NtroInitializationTask();
		initializationTask.addSubTask(new InitializationTaskJavaFx(primaryStage));

		return initializationTask;
	}

}
