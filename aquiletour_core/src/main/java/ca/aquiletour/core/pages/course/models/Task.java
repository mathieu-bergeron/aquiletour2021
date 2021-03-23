package ca.aquiletour.core.pages.course.models;

import ca.ntro.core.models.NtroModelValue;

public class Task implements NtroModelValue {
	
	private String id = "";
	private String title = "";
	
	private ObservableTaskIdList previousTasks = new ObservableTaskIdList();
	private ObservableTaskIdList subTasks = new ObservableTaskIdList();
	private ObservableTaskIdList nextTasks = new ObservableTaskIdList();

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

	public ObservableTaskIdList getPreviousTasks() {
		return previousTasks;
	}

	public void setPreviousTasks(ObservableTaskIdList previousTasks) {
		this.previousTasks = previousTasks;
	}

	public ObservableTaskIdList getSubTasks() {
		return subTasks;
	}

	public void setSubTasks(ObservableTaskIdList subTasks) {
		this.subTasks = subTasks;
	}

	public ObservableTaskIdList getNextTasks() {
		return nextTasks;
	}

	public void setNextTasks(ObservableTaskIdList nextTasks) {
		this.nextTasks = nextTasks;
	}
}
