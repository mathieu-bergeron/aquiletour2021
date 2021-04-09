package ca.aquiletour.core.pages.course.messages;

import ca.ntro.core.Path;
import ca.ntro.messages.NtroMessage;

public class ShowTaskMessage extends NtroMessage {
	
	private Path taskPath = new Path("/");

	public Path getTaskPath() {
		return taskPath;
	}

	public void setTaskPath(Path taskPath) {
		this.taskPath = taskPath;
	}
}
