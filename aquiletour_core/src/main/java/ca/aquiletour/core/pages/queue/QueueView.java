package ca.aquiletour.core.pages.queue;

import ca.ntro.core.mvc.NtroView;

public interface QueueView extends NtroView {

	void initializeCloseQueueButton(String courseId);
	void deleteAppointment(String appointmentId);
	void insertAppointment(int index, AppointmentView appointmentView);
	void clearQueue();
}
