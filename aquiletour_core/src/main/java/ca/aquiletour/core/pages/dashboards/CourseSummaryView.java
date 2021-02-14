package ca.aquiletour.core.pages.dashboards;

import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.ntro.core.mvc.NtroView;

public interface CourseSummaryView extends NtroView {
	
	void displaySummary(CourseSummary course);

}
