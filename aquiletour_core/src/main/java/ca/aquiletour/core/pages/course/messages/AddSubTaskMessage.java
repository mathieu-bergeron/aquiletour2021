package ca.aquiletour.core.pages.course.messages;

import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.users.User;
import ca.ntro.core.Path;
import ca.ntro.messages.NtroUserMessage;

public class AddSubTaskMessage extends NtroUserMessage<User> {
	
	private String courseId;
	private Path parentPath;
	private Task subTask;

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

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
