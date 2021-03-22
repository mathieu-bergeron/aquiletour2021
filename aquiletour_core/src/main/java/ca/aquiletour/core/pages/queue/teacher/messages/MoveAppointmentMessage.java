package ca.aquiletour.core.pages.queue.teacher.messages;

import ca.aquiletour.core.models.users.User;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroUserMessage;

public class MoveAppointmentMessage extends NtroUserMessage<User> {

	
	private String appointmentId;
	private String appointmentDestinationId;
	private String appointmentDepartureId;
	private String courseId;

	public String getAppointmentId() {
		T.call(this);

		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		T.call(this);

		this.appointmentId = appointmentId;
	}

	public String getappointmentDestinationId() {
		return appointmentDestinationId;
	}

	public void setappointmentDestinationId(String appointmentDestinationId) {
		this.appointmentDestinationId = appointmentDestinationId;
	}

	public String getappointmentDepartureId() {
		return appointmentDepartureId;
	}

	public void setappointmentDepartureId(String appointmentDepartureId) {
		this.appointmentDepartureId = appointmentDepartureId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	

}
