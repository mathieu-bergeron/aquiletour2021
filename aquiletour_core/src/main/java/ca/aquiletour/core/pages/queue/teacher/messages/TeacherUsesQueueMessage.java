package ca.aquiletour.core.pages.queue.teacher.messages;

import ca.aquiletour.core.models.users.User;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.NtroUserMessage;

public class TeacherUsesQueueMessage extends NtroUserMessage<User> {

	
	private String courseId;

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	
}
