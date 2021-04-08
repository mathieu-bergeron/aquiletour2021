package ca.aquiletour.core.pages.dashboards;

import ca.ntro.core.mvc.NtroView;

public interface DashboardView extends NtroView  {

	void appendCourse(String queueId, DashboardCourseView courseView);
	void deleteCourse(String queueId);
	void clearCourses();
	
}
