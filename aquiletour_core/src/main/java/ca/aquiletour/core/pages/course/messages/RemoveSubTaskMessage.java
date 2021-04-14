package ca.aquiletour.core.pages.course.messages;

import ca.aquiletour.core.messages.course.CourseMessage;
import ca.aquiletour.core.models.users.User;
import ca.ntro.core.Path;
import ca.ntro.messages.NtroUserMessage;

public class RemoveSubTaskMessage extends CourseMessage {
	
	private Path taskToModify;
	private Path taskToRemove;

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
