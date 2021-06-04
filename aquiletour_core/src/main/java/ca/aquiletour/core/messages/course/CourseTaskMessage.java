package ca.aquiletour.core.messages.course;

import ca.aquiletour.core.models.paths.TaskPath;

public class CourseTaskMessage extends CourseMessage {

	private TaskPath taskPath = TaskPath.fromRawPath("/");

	public TaskPath getTaskPath() {
		return taskPath;
	}

	public void setTaskPath(TaskPath taskPath) {
		this.taskPath = taskPath;
	}
}
