package ca.aquiletour.core.pages.users.messages;

import ca.aquiletour.core.models.users.User;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;

public class DeleteUserFromCourseMessage extends NtroMessage {

	private String userId, courseId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}




}
