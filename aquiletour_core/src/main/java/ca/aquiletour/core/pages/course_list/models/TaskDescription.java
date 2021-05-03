package ca.aquiletour.core.pages.course_list.models;

import ca.aquiletour.core.models.courses.base.Task;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class TaskDescription implements NtroModelValue {
	
	private String taskId = "";
	private String taskTitle = "";

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public static TaskDescription fromTask(Task task) {
		T.call(TaskDescription.class);

		TaskDescription description = new TaskDescription();
		description.setTaskTitle(task.getTitle().getValue());
		
		int nameCount = task.getPath().nameCount();
		String id = task.getPath().name(nameCount-1);
		
		description.setTaskId(id);

		return description;
	}
}
