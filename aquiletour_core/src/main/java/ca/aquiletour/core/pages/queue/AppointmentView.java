package ca.aquiletour.core.pages.queue;

import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.mvc.NtroView;

public interface AppointmentView extends NtroView {
	
	void displayAppointement(String queueId, String userId, Appointment appointment);

}
