package ca.aquiletour.core.pages.course.messages;

import ca.aquiletour.core.messages.course.CourseTaskMessage;
import ca.ntro.core.Path;

public class DeleteTaskMessage extends CourseTaskMessage {
	
	private Path taskToDelete;

	public Path getTaskToDelete() {
		return taskToDelete;
	}

	public void setTaskToDelete(Path taskToDelete) {
		this.taskToDelete = taskToDelete;
	}
}
