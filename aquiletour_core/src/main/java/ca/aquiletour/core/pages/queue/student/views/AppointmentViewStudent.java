package ca.aquiletour.core.pages.queue.student.views;

import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.aquiletour.core.pages.queue.views.AppointmentView;

public interface AppointmentViewStudent extends AppointmentView {
	
	void displayAppointement(Appointment appointment);

}
