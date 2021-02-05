package ca.aquiletour.core.pages.queue.messages;

import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;

public class DeleteAppointmentMessage extends NtroMessage {

	
	//private Appointment appointment;
	private int appointmentId;

	public void deleteAppointment(int appointmentId) {
		T.call(this);
		
		this.appointmentId = appointmentId;
	}
	
	public int getAppointmentId() {
		T.call(this);
		
		return appointmentId;
	}
}
