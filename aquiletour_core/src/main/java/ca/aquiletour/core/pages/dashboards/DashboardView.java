package ca.aquiletour.core.pages.dashboards;

import ca.ntro.core.mvc.NtroView;

public interface DashboardView extends NtroView  {
	
	void appendCourse(CourseSummaryView courseView);

}