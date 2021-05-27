package ca.aquiletour.core.pages.queue.teacher.views;

import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.aquiletour.core.pages.queue.views.AppointmentView;
import ca.ntro.core.mvc.NtroView;

public interface AppointmentViewTeacher extends AppointmentView {
	
	void displayAppointement(Appointment appointment);

}
