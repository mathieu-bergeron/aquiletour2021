package ca.aquiletour.core.pages.course.messages;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course.models.Task;
import ca.ntro.messages.NtroUserMessage;

public class AddTaskMessage extends NtroUserMessage<User> {
	
	private String courseId;
	private Task task;

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
}
