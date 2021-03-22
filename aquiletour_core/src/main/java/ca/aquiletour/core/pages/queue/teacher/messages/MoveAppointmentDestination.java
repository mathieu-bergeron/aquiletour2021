package ca.aquiletour.core.pages.queue.teacher.messages;

public enum MoveAppointmentDestination {
	
	AFTER,
	BEFORE;
	
	private String appointmentId;

	public String getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}
}
