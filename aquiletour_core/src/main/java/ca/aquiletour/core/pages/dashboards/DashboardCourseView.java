package ca.aquiletour.core.pages.dashboards;

import ca.aquiletour.core.pages.dashboards.values.CourseDashboard;
import ca.ntro.core.mvc.NtroView;

public interface DashboardCourseView extends NtroView {
	
	void displaySummary(CourseDashboard course);
	void displayStatus(String QueueId, boolean myAppointment, boolean teacherAvailable);
	void displayNumberOfAppointments(int numberOfAppointments);
	void displayNumberOfStudents(int numberOfStudents);

}
