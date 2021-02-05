package ca.aquiletour.core.pages.dashboard;

import ca.aquiletour.core.pages.dashboard.values.CourseSummary;
import ca.ntro.core.mvc.NtroView;

public interface CourseSummaryView extends NtroView {
	
	void displaySummary(CourseSummary course);

}
