package ca.aquiletour.core.pages.queue.teacher.messages;

import ca.ntro.messages.NtroMessage;

public class ShowTeacherQueueMessage extends NtroMessage {
	
	private String courseId;

	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
}
