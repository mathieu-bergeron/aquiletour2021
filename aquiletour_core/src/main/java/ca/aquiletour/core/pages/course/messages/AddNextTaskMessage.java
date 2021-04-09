package ca.aquiletour.core.pages.course.messages;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course.models.Task;
import ca.ntro.core.Path;
import ca.ntro.messages.NtroUserMessage;

public class AddNextTaskMessage extends NtroUserMessage<User> {
	
	private String courseId;
	private Path previousPath;
	private Task nextTask;

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public Path getPreviousPath() {
		return previousPath;
	}

	public void setPreviousPath(Path previousPath) {
		this.previousPath = previousPath;
	}

	public Task getNextTask() {
		return nextTask;
	}

	public void setNextTask(Task nextTask) {
		this.nextTask = nextTask;
	}
	
}
