package ca.aquiletour.core.pages.dashboards.teacher;

import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.ntro.core.mvc.NtroView;

public interface TeacherCourseSummaryView extends NtroView {
	
	void displaySummary(CourseSummary course);

}
