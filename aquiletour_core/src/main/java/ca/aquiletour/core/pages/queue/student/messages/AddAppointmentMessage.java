package ca.aquiletour.core.pages.queue.student.messages;

import java.util.Queue;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;

public class AddAppointmentMessage extends NtroMessage {

	private Appointment appointment;
	private User user;
	private String courseId;

	public void setAppointment(Appointment appointment) {
		T.call(this);
		
		this.appointment = appointment;
	}
	
	public Appointment getAppointment() {
		T.call(this);
		
		return appointment;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
}
