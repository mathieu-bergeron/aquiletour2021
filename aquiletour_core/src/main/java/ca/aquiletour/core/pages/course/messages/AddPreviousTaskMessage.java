package ca.aquiletour.core.pages.course.messages;

import ca.aquiletour.core.messages.course.CourseTaskMessage;
import ca.aquiletour.core.models.courses.base.Task;
import ca.ntro.core.Path;

public class AddPreviousTaskMessage extends CourseTaskMessage {
	
	private Path nextPath;
	private Task previousTask;

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
