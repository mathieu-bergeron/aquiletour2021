package ca.aquiletour.core.pages.course.messages;

import ca.ntro.core.Path;
import ca.ntro.messages.NtroMessage;

public class ShowCourseMessage extends NtroMessage {
	
	private String courseId;
	private Path taskPath = new Path("/");

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public Path getTaskPath() {
		return taskPath;
	}

	public void setTaskPath(Path taskPath) {
		this.taskPath = taskPath;
	}
}
