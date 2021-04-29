package ca.aquiletour.core.pages.queue.student;

import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.aquiletour.core.pages.queue.views.AppointmentView;
import ca.ntro.core.mvc.NtroView;

public interface AppointmentViewStudent extends AppointmentView {
	
	void displayAppointement(Appointment appointment);

}
