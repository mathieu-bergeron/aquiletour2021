package ca.aquiletour.javafx;

import ca.ntro.core.Ntro;
import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.mvc.view.NtroView;
import ca.ntro.core.system.trace.T;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class NtroWindowFx extends NtroWindow {
	
	private Stage primaryStage;
	
	public NtroWindowFx(Stage primaryStage) {
		super();
		T.call(this);

		this.primaryStage = primaryStage;
		
		initializeStage();
	}

	private void initializeStage() {
		T.call(this);
		
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(600);

		primaryStage.setMaxWidth(800);
		primaryStage.setMaxHeight(600);
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				T.call(this);
				
				Ntro.appCloser().close();
			}
		});
		
		primaryStage.show();
	}

	@Override
	public void installRootView(NtroView rootView) {
		T.call(this);
		
		// TODO
		//primaryStage.setScene(viewLoader.createScene(800,600));
	}

}
