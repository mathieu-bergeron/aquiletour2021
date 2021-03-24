package ca.aquiletour.core.pages.course.models;

import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class Task implements NtroModelValue, TaskNode {
	
	private TaskGraph graph;
	
	private Path path = new Path();
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

	public Path getPath() {
		return path;
	}

	public void setPath(Path taskPath) {
		this.path = taskPath;
	}

	private Path parentTaskPath() {
		Path parentTaskPath = new Path();

		if(path.size() > 1) {
			parentTaskPath = path.subPath(0, path.size() - 2);
		}

		return parentTaskPath;
	}

	public Task parent() {
		return graph.findTaskByPath(parentTaskPath());
	}

	public void addSubTask(Task task) {
		T.call(this);
		
		subTasks.addItem(task.getPath().toString());
	}
	
	public TaskNode asNode() {
		T.call(this);
		
		return this;
	}

	public TaskGraph getGraph() {
		return graph;
	}

	public void setGraph(TaskGraph graph) {
		this.graph = graph;
	}
	
	public void forEachSubTask(TaskLambda lambda) {
		T.call(this);

		for(String subTaskId : getSubTasks().getValue()) {
			Task subTask = graph.findTaskByPath(new Path(subTaskId));
			lambda.execute(subTask);
		}
	}

}
