package ca.aquiletour.core.pages.queue.teacher.messages;

import ca.aquiletour.core.models.user.User;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroUserMessage;

public class DeleteAppointmentMessage extends NtroUserMessage<User> {
	
	private String appointmentId;
	private String courseId;

	public String getAppointmentId() {
		T.call(this);

		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		T.call(this);

		this.appointmentId = appointmentId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	
	

}
