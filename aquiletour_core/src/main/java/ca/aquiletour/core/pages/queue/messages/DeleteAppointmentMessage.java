package ca.aquiletour.core.pages.queue.messages;

import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;

public class DeleteAppointmentMessage extends NtroMessage {

	
	private String appointmentId;

	public String getAppointmentId() {
		T.call(this);

		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		T.call(this);

		this.appointmentId = appointmentId;
	}

	

}
