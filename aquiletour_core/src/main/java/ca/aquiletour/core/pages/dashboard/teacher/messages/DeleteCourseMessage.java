package ca.aquiletour.core.pages.dashboard.teacher.messages;

import ca.aquiletour.core.models.user.User;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroUserMessage;

public class DeleteCourseMessage extends NtroUserMessage<User> {

	private String courseId;

	public void setCourseId(String courseId) {
		T.call(this);
		
		this.courseId = courseId;
	}
	
	public String getCourseId() {
		T.call(this);
		
		return courseId;
	}
}
