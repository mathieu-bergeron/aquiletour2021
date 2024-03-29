package ca.aquiletour.core.models.session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.pages.dashboard.student.models.CurrentTaskStudent;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class CurrentTasksByCourseKey implements NtroModelValue {
	
	private Map<String, TaskTitleByTaskKey> currentTasksByCourseKey = new HashMap<>();

	public Map<String, TaskTitleByTaskKey> getCurrentTasksByCourseKey() {
		return currentTasksByCourseKey;
	}

	public void setCurrentTasksByCourseKey(Map<String, TaskTitleByTaskKey> currentTasksByCourseKey) {
		this.currentTasksByCourseKey = currentTasksByCourseKey;
	}

	public void updateCurrentTasks(CoursePath coursePath, List<CurrentTaskStudent> currentTasks) {
		T.call(this);
		
		String courseKey = coursePath.toKey();
		
		TaskTitleByTaskKey taskTitleByTaskKey = getCurrentTasksByCourseKey().get(courseKey);
		if(taskTitleByTaskKey == null) {
			taskTitleByTaskKey = new TaskTitleByTaskKey();
			getCurrentTasksByCourseKey().put(courseKey, taskTitleByTaskKey);
		}
		
		taskTitleByTaskKey.updateCurrentTasks(currentTasks);
	}
	
	public CurrentTasksByCourseKey clone() {
		T.call(this);
		
		CurrentTasksByCourseKey clone = new CurrentTasksByCourseKey();
		
		for(Map.Entry<String, TaskTitleByTaskKey> entry : getCurrentTasksByCourseKey().entrySet()) {
			
			clone.getCurrentTasksByCourseKey().put(entry.getKey(), entry.getValue().clone());
		}
		
		return clone;
	}
}
