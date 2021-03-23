package ca.aquiletour.core.pages.course.messages;

import ca.ntro.messages.NtroMessage;

public class ShowCourseMessage extends NtroMessage {
	
	String courseId;

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
}
