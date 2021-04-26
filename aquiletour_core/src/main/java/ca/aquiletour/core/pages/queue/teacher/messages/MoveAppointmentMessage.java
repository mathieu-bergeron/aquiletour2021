package ca.aquiletour.core.pages.queue.teacher.messages;

import ca.aquiletour.core.models.users.User;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroUserMessage;

public class MoveAppointmentMessage extends NtroUserMessage<User> {

	private String appointmentId;
	private String destinationId;
	private String beforeOrAfter;
	private String courseId;

	public String getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}
	public String getDestinationId() {
		return destinationId;
	}
	public void setDestinationId(String destinationId) {
		this.destinationId = destinationId;
	}
	public String getBeforeOrAfter() {
		return beforeOrAfter;
	}
	public void setBeforeOrAfter(String beforeOrAfter) {
		this.beforeOrAfter = beforeOrAfter;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
}
