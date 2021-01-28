package ca.aquiletour.javafx.pages.dashboard;

import java.net.URL;
import java.util.ResourceBundle;

import ca.aquiletour.core.pages.dashboard.DashboardView;
import ca.ntro.core.system.trace.T;
import ca.ntro.javafx.NtroViewFx;

public class DashboardViewFx extends NtroViewFx implements DashboardView {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		T.call(this);
		
	}

}
