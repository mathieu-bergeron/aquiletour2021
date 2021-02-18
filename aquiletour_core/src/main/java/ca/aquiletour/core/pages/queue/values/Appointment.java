package ca.aquiletour.core.pages.queue.values;

import ca.ntro.core.models.properties.NtroModelValue;

public class Appointment extends NtroModelValue {
	
	private String appointmentId;
	private String time;
	

	public String getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
