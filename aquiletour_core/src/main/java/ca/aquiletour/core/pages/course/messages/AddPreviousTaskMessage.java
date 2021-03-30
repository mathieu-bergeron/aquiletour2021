package ca.aquiletour.core.pages.course.messages;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course.models.Task;
import ca.ntro.core.Path;
import ca.ntro.messages.NtroUserMessage;

public class AddPreviousTaskMessage extends NtroUserMessage<User> {
	
	private String courseId;
	private Path nextPath;
	private Task previousTask;

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public Path getNextPath() {
		return nextPath;
	}

	public void setNextPath(Path nextPath) {
		this.nextPath = nextPath;
	}

	public Task getPreviousTask() {
		return previousTask;
	}

	public void setPreviousTask(Task previousTask) {
		this.previousTask = previousTask;
	}
}
