package ca.aquiletour.core.pages.queue.teacher.views;

import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.aquiletour.core.pages.queue.views.AppointmentView;

public interface AppointmentViewTeacher extends AppointmentView {
	
	void displayAppointement(Appointment appointment);

}
