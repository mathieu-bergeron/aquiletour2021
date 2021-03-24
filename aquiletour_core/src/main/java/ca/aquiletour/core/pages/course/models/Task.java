package ca.aquiletour.core.pages.course.models;

import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class Task implements NtroModelValue, TaskNode {
	
	private Path taskPath = new Path();
	private String title = "";
	
	private ObservableTaskIdList previousTasks = new ObservableTaskIdList();
	private ObservableTaskIdList subTasks = new ObservableTaskIdList();
	private ObservableTaskIdList nextTasks = new ObservableTaskIdList();

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

	public void removeObservers() {
		T.call(this);
		
		getPreviousTasks().removeObservers();
		getSubTasks().removeObservers();
		getNextTasks().removeObservers();
	}

	public Path getTaskPath() {
		return taskPath;
	}

	public void setTaskPath(Path taskPath) {
		this.taskPath = taskPath;
	}

	public Path parentTaskPath() {
		Path parentTaskPath = new Path();

		if(taskPath.size() > 1) {
			parentTaskPath = taskPath.subPath(0, taskPath.size() - 2);
		}

		return parentTaskPath;
	}

	public void addSubTask(Task task) {
		T.call(this);
		
		subTasks.addItem(task.getTaskPath().toString());
	}
	
	public TaskNode asNode() {
		T.call(this);
		
		return this;
	}
}
