package ca.aquiletour.core.pages.course.messages;

import ca.aquiletour.core.messages.course.CourseTaskMessage;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.user.User;
import ca.ntro.core.Path;
import ca.ntro.messages.NtroUserMessage;

public class AddNextTaskMessage extends CourseTaskMessage {
	
	private Path previousPath;
	private Task nextTask;

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
