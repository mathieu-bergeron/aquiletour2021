package ca.aquiletour.core.pages.queue;

import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.mvc.view.NtroView;

public interface QueueView extends NtroView {

	void appendAppointement(Appointment appointment);
	void deleteAppointment(String appointmentId);

}
