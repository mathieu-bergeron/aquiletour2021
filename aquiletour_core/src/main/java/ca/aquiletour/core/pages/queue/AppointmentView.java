package ca.aquiletour.core.pages.queue;

import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.mvc.view.NtroView;

public interface AppointmentView extends NtroView {
	
	void displayAppointement(Appointment appointment);

}
