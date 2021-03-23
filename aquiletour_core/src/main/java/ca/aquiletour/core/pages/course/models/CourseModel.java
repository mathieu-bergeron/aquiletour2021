package ca.aquiletour.core.pages.course.models;

import ca.ntro.core.models.NtroModel;

public class CourseModel implements NtroModel {
	
	private ObservableTaskList tasks = new ObservableTaskList();

	public ObservableTaskList getTasks() {
		return tasks;
	}

	public void setTasks(ObservableTaskList tasks) {
		this.tasks = tasks;
	}
	
}
