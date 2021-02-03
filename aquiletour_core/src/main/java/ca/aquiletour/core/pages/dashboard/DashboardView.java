package ca.aquiletour.core.pages.dashboard;

import ca.aquiletour.core.pages.dashboard.values.CourseSummary;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.mvc.ViewLoader;

public interface DashboardView extends NtroView  {
	
	void setCourseSummaryViewLoader(ViewLoader courseSummaryViewLoader);
	void appendCourse(CourseSummary course);

}
