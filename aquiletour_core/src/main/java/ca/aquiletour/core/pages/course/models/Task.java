package ca.aquiletour.core.pages.course.models;

import ca.ntro.core.models.NtroModelValue;

public class Task implements NtroModelValue {
	
	private String id = "";
	private String title = "";
	
	private ObservableTaskList previousTasks = new ObservableTaskList();
	private ObservableTaskList subTasks = new ObservableTaskList();
	private ObservableTaskList nextTasks = new ObservableTaskList();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ObservableTaskList getPreviousTasks() {
		return previousTasks;
	}

	public void setPreviousTasks(ObservableTaskList previousTasks) {
		this.previousTasks = previousTasks;
	}

	public ObservableTaskList getSubTasks() {
		return subTasks;
	}

	public void setSubTasks(ObservableTaskList subTasks) {
		this.subTasks = subTasks;
	}

	public ObservableTaskList getNextTasks() {
		return nextTasks;
	}

	public void setNextTasks(ObservableTaskList nextTasks) {
		this.nextTasks = nextTasks;
	}
}
