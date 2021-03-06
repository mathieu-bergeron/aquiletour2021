package ca.ntro.javafx;

import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.system.trace.T;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

public abstract class NtroViewFx implements NtroView, Initializable {
	
	private Parent parent;
	
	void registerParent(Parent parent) {
		T.call(this);
		
		this.parent = parent;
	}

	public Scene createScene(int width, int height) {
		T.call(this);

		return new Scene(parent, width, height);
	}

	public Parent parent() {
		return parent;
	}

}
