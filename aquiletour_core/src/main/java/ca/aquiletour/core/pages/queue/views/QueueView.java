package ca.aquiletour.core.pages.queue.views;

import ca.ntro.core.mvc.NtroView;

public interface QueueView extends NtroView {

	void deleteAppointment(String appointmentId);
	void insertAppointment(int index, AppointmentView appointmentView);
	void clearQueue();
}
