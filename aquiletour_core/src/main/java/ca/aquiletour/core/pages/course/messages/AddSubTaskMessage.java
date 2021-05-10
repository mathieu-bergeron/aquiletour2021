package ca.aquiletour.core.pages.course.messages;

import ca.aquiletour.core.messages.course.CourseMessage;

import ca.aquiletour.core.models.courses.base.Task;
import ca.ntro.core.Path;

public class AddSubTaskMessage extends CourseMessage {
	
	private Path parentPath;
	private Task subTask;

	public Path getParentPath() {
		return parentPath;
	}

	public void setParentPath(Path parentPath) {
		this.parentPath = parentPath;
	}

	public Task getSubTask() {
		return subTask;
	}

	public void setSubTask(Task subTask) {
		this.subTask = subTask;
	}
}
