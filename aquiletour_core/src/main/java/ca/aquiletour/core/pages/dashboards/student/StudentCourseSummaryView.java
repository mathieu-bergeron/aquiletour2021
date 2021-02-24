package ca.aquiletour.core.pages.dashboards.student;

import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.ntro.core.mvc.NtroView;

public interface StudentCourseSummaryView extends NtroView {
	
	void displaySummary(CourseSummary course);

}