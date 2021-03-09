package ca.aquiletour.core.pages.queue.teacher.messages;

import ca.aquiletour.core.models.users.User;
import ca.ntro.messages.NtroMessage;

public class TeacherClosesQueueMessage extends NtroMessage {

	
	private User teacher;
	private String courseId;

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public User getTeacher() {
		return teacher;
	}

	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}
	
	

}
