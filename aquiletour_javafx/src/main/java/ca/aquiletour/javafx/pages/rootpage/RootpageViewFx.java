package ca.aquiletour.javafx.pages.rootpage;

import java.net.URL;
import java.util.ResourceBundle;

import ca.aquiletour.core.pages.rootpage.RootpageView;
import ca.ntro.core.system.trace.T;
import javafx.fxml.Initializable;

public class RootpageViewFx implements RootpageView, Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		T.call(this);
		
	}

}
