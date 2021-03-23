package ca.aquiletour.core.pages.course.models;

import ca.ntro.core.models.NtroModel;

public class CourseModel implements NtroModel {
	
	private ObservableTaskMap rootTasks = new ObservableTaskMap();

	public ObservableTaskMap getRootTasks() {
		return rootTasks;
	}

	public void setRootTasks(ObservableTaskMap rootTasks) {
		this.rootTasks = rootTasks;
	}
}
