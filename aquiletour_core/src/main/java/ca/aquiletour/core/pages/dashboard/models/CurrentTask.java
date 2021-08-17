package ca.aquiletour.core.pages.dashboard.models;

import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.paths.TaskPath;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.system.trace.T;

public abstract class CurrentTask implements NtroModelValue {
	
	private TaskPath taskPath;
	private StoredString taskTitle = new StoredString();
	
	public CurrentTask() {
		T.call(this);
	}

	public CurrentTask(Task task) {
		super();
		T.call(this);
		
		setTaskPath(task.getPath());
		updateTaskTitle(task.getTitle().getValue());
	}
	
	public void updateTaskTitle(String title) {
		T.call(this);
		
		getTaskTitle().set(title);
	}

	public TaskPath getTaskPath() {
		return taskPath;
	}

	public void setTaskPath(TaskPath taskPath) {
		this.taskPath = taskPath;
	}

	public StoredString getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(StoredString taskTitle) {
		this.taskTitle = taskTitle;
	}

}
