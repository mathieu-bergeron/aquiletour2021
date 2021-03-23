package ca.aquiletour.core.pages.course.models;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class CourseModel implements NtroModel {
	
	private ObservableTaskMap allTasks = new ObservableTaskMap();
	private ObservableTaskIdList rootTasks = new ObservableTaskIdList();

	public ObservableTaskMap getAllTasks() {
		return allTasks;
	}
	public void setAllTasks(ObservableTaskMap allTasks) {
		this.allTasks = allTasks;
	}
	public ObservableTaskIdList getRootTasks() {
		return rootTasks;
	}
	public void setRootTasks(ObservableTaskIdList rootTasks) {
		this.rootTasks = rootTasks;
	}

	public Task getTaskById(String taskId) {
		T.call(this);
		
		return getAllTasks().valueOf(taskId);
	}
}
