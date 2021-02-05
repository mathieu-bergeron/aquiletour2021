package ca.aquiletour.core.pages.queue.messages;

import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;

public class AddAppointmentMessage extends NtroMessage {

	private Appointment appointment;

	public void setAppointment(Appointment appointment) {
		T.call(this);
		
		this.appointment = appointment;
	}
	
	public Appointment getAppointment() {
		T.call(this);
		
		return appointment;
	}

}
