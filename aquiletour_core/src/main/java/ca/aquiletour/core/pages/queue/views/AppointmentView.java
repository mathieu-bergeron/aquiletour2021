package ca.aquiletour.core.pages.queue.views;

import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.ntro.core.mvc.NtroView;

public interface AppointmentView extends NtroView {
	
	void displayAppointement(String queueId, String userId, Appointment appointment);

}
