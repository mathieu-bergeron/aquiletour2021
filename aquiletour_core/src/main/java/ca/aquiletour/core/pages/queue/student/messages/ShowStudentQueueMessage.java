package ca.aquiletour.core.pages.queue.student.messages;

import ca.ntro.messages.NtroMessage;

public class ShowStudentQueueMessage extends NtroMessage {
	
	private String courseId;

	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
}
