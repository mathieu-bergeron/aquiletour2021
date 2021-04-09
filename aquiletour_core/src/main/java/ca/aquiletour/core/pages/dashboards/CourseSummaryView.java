package ca.aquiletour.core.pages.dashboards;

import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.ntro.core.mvc.NtroView;

public interface CourseSummaryView extends NtroView {
	
	void displaySummary(CourseSummary course);
	void displayStatus(String QueueId, boolean myAppointment, boolean teacherAvailable);
	void displayNumberOfAppointments(int numberOfAppointments);
	void displayNumberOfStudents(int numberOfStudents);

}
