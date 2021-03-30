package ca.aquiletour.core.pages.course.messages;

import ca.aquiletour.core.models.users.User;
import ca.ntro.core.Path;
import ca.ntro.messages.NtroUserMessage;

public class RemoveNextTaskMessage extends NtroUserMessage<User> {
	
	private String courseId;
	private Path taskToModify;
	private Path taskToRemove;

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public Path getTaskToModify() {
		return taskToModify;
	}

	public void setTaskToModify(Path taskToModify) {
		this.taskToModify = taskToModify;
	}

	public Path getTaskToRemove() {
		return taskToRemove;
	}

	public void setTaskToRemove(Path taskToRemove) {
		this.taskToRemove = taskToRemove;
	}
	
	
	

}
