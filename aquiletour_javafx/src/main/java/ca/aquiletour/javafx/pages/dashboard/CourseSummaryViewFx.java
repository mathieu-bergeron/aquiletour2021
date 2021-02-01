package ca.aquiletour.javafx.pages.dashboard;

import java.net.URL;
import java.util.ResourceBundle;

import ca.aquiletour.core.pages.dashboard.CourseSummaryView;
import ca.aquiletour.core.pages.dashboard.values.CourseSummary;
import ca.ntro.core.system.trace.T;
import ca.ntro.javafx.NtroViewFx;

public class CourseSummaryViewFx extends NtroViewFx implements CourseSummaryView {
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		T.call(this);
		
	}

	@Override
	public void displaySummary(CourseSummary course) {
		T.call(this);

	}

}
