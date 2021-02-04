package ca.aquiletour.core.pages.queue;

import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.mvc.view.NtroView;
import ca.ntro.core.mvc.view.ViewLoader;

public interface QueueView extends NtroView {
	
	void setAppointmentViewLoader(ViewLoader appointmentViewLoader);
	void appendAppointement(Appointment appointment);
	void deleteAppointment(String appointmentId);

}
