package ca.aquiletour.core.pages.course.models;

import java.util.ArrayList;
import java.util.List;

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

	private Path parentPath() {
		T.call(this);

		Path parentPath = null;
		
		if(getPath().nameCount() > 1) {

			parentPath = getPath().subPath(0, getPath().nameCount() - 2);

		}else if(getPath().nameCount() == 1) {

			parentPath = new Path();
		}
		
		return parentPath;
	}

	public Task parent() {
		T.call(this);

		Task parent = null;
		Path parentPath = parentPath();

		if(parentPath != null) {

			parent = graph.findTaskByPath(parentPath);
		}

		return parent;
	}

	public Task rootParent() {
		Task rootParent = null;
		Task parent = parent();
		if(parent != null) {
			rootParent = parent.rootParent();
		}
		return rootParent;
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

	public void forEachNextTask(TaskLambda lambda) {
		T.call(this);

		for(String nextTaskId : getNextTasks().getValue()) {
			Task nextTask = graph.findTaskByPath(new Path(nextTaskId));
			lambda.execute(nextTask);
		}
	}

	public void forEachPreviousTask(TaskLambda lambda) {
		T.call(this);

		for(String previousTaskId : getPreviousTasks().getValue()) {
			Task previousTask = graph.findTaskByPath(new Path(previousTaskId));
			lambda.execute(previousTask);
		}
	}

	public void forEachSibling(TaskLambda lambda) {
		T.call(this);
		
		Task parent = parent();
		parent.forEachSubTask(sib -> {
			if(sib != this) {
				lambda.execute(sib);
			}
		});
	}

	private List<Task> trunk() {
		
		T.call(this);
		
		List<Task> trunk = null;
		Task parent = parent();
		
		
		if(parent != null) {
			
			System.out.println("parent: " + parent.getPath().toString());
			trunk = parent.trunk();

		}else {

			trunk = new ArrayList<>();
		}
		
		trunk.add(this);

		return trunk;
	}

	public TaskBreadcrumbs breadcrumbs() {
		T.call(this);
		
		TaskBreadcrumbs breadcrumbs = new TaskBreadcrumbs();
		
		breadcrumbs.setTrunk(trunk());

		return breadcrumbs;
	}

	public String id() {
		return getPath().toString();
	}

	public void addNextTask(Task nextTask) {
		T.call(this);
		
	}

}
