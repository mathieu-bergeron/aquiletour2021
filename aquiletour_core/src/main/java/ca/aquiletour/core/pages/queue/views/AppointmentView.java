package ca.aquiletour.core.pages.queue.views;

import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.models.NtroDate;

public interface AppointmentView extends NtroView {
	
	void displayAppointement(String queueId, String userId, Appointment appointment);

	void displayCourseTitle(String courseTitle);
	void displayTaskTitle(String taskTitle);
	void displayComment(String comment);

	void appendTag(String tag);
	void clearTags();

	void dislayTime(NtroDate appointmentTime);

}
