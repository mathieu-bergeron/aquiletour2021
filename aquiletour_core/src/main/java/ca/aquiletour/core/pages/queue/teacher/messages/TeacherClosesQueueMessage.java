package ca.aquiletour.core.pages.queue.teacher.messages;

import ca.aquiletour.core.models.user.User;
import ca.ntro.messages.NtroUserMessage;

public class TeacherClosesQueueMessage extends NtroUserMessage<User> {
	
	private String courseId;

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
}
