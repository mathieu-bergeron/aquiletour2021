package ca.aquiletour.core.pages.queue;

import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.mvc.NtroView;

public interface QueueView extends NtroView {

	void initializeCloseQueueButton(String courseId);
	void appendAppointement(Appointment appointment, AppointmentView appointmentView);
	void deleteAppointment(String appointmentId);
	void insertAppointment(int index, Appointment appointment, AppointmentView appointmentView);
	void clearQueue();
}
