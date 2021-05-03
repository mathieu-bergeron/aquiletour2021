package ca.aquiletour.core.pages.course.messages;

import ca.aquiletour.core.messages.course.CourseMessage;
import ca.ntro.core.Path;

public class DeleteTaskMessage extends CourseMessage {
	
	private Path taskToDelete;

	public Path getTaskToDelete() {
		return taskToDelete;
	}

	public void setTaskToDelete(Path taskToDelete) {
		this.taskToDelete = taskToDelete;
	}
}
