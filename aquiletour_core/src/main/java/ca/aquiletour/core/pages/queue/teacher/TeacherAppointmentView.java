package ca.aquiletour.core.pages.queue.teacher;

import ca.aquiletour.core.pages.queue.AppointmentView;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.mvc.NtroView;

public interface TeacherAppointmentView extends AppointmentView {
	
	void displayAppointement(Appointment appointment);

}
