package ca.aquiletour.core.pages.dashboards;

import ca.aquiletour.core.pages.dashboards.values.DashboardItem;
import ca.ntro.core.mvc.NtroView;

public interface DashboardCourseView extends NtroView {
	
	void displaySummary(DashboardItem course);
	void displayStatus(String QueueId, boolean myAppointment, boolean teacherAvailable);
	void displayNumberOfAppointments(int numberOfAppointments);
	void displayNumberOfStudents(int numberOfStudents);

}
