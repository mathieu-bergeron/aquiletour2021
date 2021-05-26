package ca.aquiletour.core.messages.course;

import ca.ntro.core.Path;

public class CourseTaskMessage extends CourseMessage {

	private Path taskPath = new Path("/");

	public Path getTaskPath() {
		return taskPath;
	}

	public void setTaskPath(Path taskPath) {
		this.taskPath = taskPath;
	}
}
