package ca.aquiletour.core.pages.course.messages;

import ca.aquiletour.core.models.users.User;
import ca.ntro.core.Path;
import ca.ntro.messages.NtroUserMessage;

public class DeleteTaskMessage extends NtroUserMessage<User> {
	
	private String courseId;
	private Path taskToDelete;

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public Path getTaskToDelete() {
		return taskToDelete;
	}

	public void setTaskToDelete(Path taskToDelete) {
		this.taskToDelete = taskToDelete;
	}
}
