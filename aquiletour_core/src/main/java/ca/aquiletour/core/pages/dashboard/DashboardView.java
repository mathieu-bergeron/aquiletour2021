package ca.aquiletour.core.pages.dashboard;

import ca.aquiletour.core.pages.dashboard.values.Course;
import ca.ntro.core.mvc.view.NtroView;

public interface DashboardView extends NtroView  {
	
	void appendCourse(Course course);

}
