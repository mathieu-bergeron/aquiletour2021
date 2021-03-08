package ca.aquiletour.core.pages.dashboards.teacher.messages;

import ca.aquiletour.core.models.users.User;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;

public class DeleteCourseMessage extends NtroMessage {
	private String courseId;
	private User user;

	public void setCourseId(String courseId) {
		T.call(this);
		
		this.courseId = courseId;
	}
	
	public String getCourseId() {
		T.call(this);
		
		return courseId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
