package ca.aquiletour.core.models.session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.aquiletour.core.pages.dashboard.student.models.CurrentTaskStudent;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class TaskTitleByTaskKey implements NtroModelValue {
	
	private Map<String, String> taskTitleByTaskKey = new HashMap<>();

	public Map<String, String> getTaskTitleByTaskKey() {
		return taskTitleByTaskKey;
	}

	public void setTaskTitleByTaskKey(Map<String, String> taskTitleByTaskKey) {
		this.taskTitleByTaskKey = taskTitleByTaskKey;
	}

	public void updateCurrentTasks(List<CurrentTaskStudent> currentTasks) {
		T.call(this);
		
		for(CurrentTaskStudent currentTask : currentTasks) {
			getTaskTitleByTaskKey().put(currentTask.getTaskPath().toKey(), currentTask.getTaskTitle().getValue());
		}
	}
}
