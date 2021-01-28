package ca.aquiletour.javafx.pages.root;

import java.net.URL;
import java.util.ResourceBundle;

import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.view.NtroView;
import ca.ntro.core.system.trace.T;
import ca.ntro.javafx.NtroViewFx;

public class RootViewFx extends NtroViewFx implements RootView {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		T.call(this);
		
	}

	@Override
	public void installSubView(NtroView page) {
		T.call(this);
		
	}

}
