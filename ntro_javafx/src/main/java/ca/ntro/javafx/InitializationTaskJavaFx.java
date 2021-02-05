package ca.ntro.javafx;

import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jdk.services.InitializationTaskJdk;
import javafx.stage.Stage;

public class InitializationTaskJavaFx extends InitializationTaskJdk {
	
	NtroWindowFx window;
	
	public InitializationTaskJavaFx(Stage primaryStage) {
		super();
		T.call(this);
		
		window = new NtroWindowFx(primaryStage);
	}

	@Override
	protected NtroWindow provideWindow() {
		__T.call(this, "provideWindow");
		
		return window;
	}
}
