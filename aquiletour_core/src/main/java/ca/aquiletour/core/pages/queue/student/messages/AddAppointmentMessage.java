package ca.aquiletour.core.pages.queue.student.messages;


import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.NtroUserMessage;

public class AddAppointmentMessage extends NtroUserMessage<User> {

	private Appointment appointment;
	private String courseId;

	public void setAppointment(Appointment appointment) {
		T.call(this);
		
		this.appointment = appointment;
	}
	
	public Appointment getAppointment() {
		T.call(this);
		
		return appointment;
	}
	
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
}
