package ca.aquiletour.core.pages.queue.views;

import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.ntro.core.mvc.NtroView;

public interface QueueView extends NtroView {

	void deleteAppointment(String appointmentId);
	void insertOrUpdateAppointment(int index, Appointment appointment, AppointmentView appointmentView);
	void clearQueue();
}
