package ca.aquiletour.web.pages.dashboard;

import ca.aquiletour.core.pages.dashboard.CourseSummaryView;
import ca.aquiletour.core.pages.dashboard.values.CourseSummary;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.mvc.NtroViewWeb;

public class CourseSummaryViewWeb extends NtroViewWeb implements CourseSummaryView {

	@Override
	public void displaySummary(CourseSummary course) {
		T.call(this);
		
	}

}
