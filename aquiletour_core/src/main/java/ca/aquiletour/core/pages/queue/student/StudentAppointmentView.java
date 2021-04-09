package ca.aquiletour.core.pages.queue.student;

import ca.aquiletour.core.pages.queue.AppointmentView;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.mvc.NtroView;

public interface StudentAppointmentView extends AppointmentView {
	
	void displayAppointement(Appointment appointment);

}
