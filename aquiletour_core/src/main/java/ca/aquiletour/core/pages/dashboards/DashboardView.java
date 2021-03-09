package ca.aquiletour.core.pages.dashboards;

import ca.aquiletour.core.pages.dashboards.student.StudentCourseSummaryView;
import ca.aquiletour.core.pages.dashboards.teacher.TeacherCourseSummaryView;
import ca.ntro.core.mvc.NtroView;

public interface DashboardView extends NtroView  {

	void appendCourse(CourseSummaryView courseView);
	void clearCourses();
	
}
