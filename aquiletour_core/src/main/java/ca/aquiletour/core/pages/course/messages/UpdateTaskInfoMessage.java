package ca.aquiletour.core.pages.course.messages;

import ca.aquiletour.core.messages.course.CourseTaskMessage;
import ca.aquiletour.core.models.dates.CourseDate;
import ca.ntro.core.Path;

public class UpdateTaskInfoMessage extends CourseTaskMessage {
	
	private Path taskToModify;
	private String taskTitle;
	private String taskDescription;
	private CourseDate endTime;

	public Path getTaskToModify() {
		return taskToModify;
	}

	public void setTaskToModify(Path taskToModify) {
		this.taskToModify = taskToModify;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public CourseDate getEndTime() {
		return endTime;
	}

	public void setEndTime(CourseDate endTime) {
		this.endTime = endTime;
	}

}
