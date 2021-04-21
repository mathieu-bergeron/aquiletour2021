package ca.aquiletour.core.models.courses.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class Task implements NtroModelValue, TaskNode {
	
	private TaskGraph graph;
	
	private Path path = new Path();
	private String title = "";
	
	private long startTime = -1;
	private long endTime = -1;

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

		return getPath().parent();
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

	public void addPreviousTask(Task previousTask) {
		T.call(this);
		
		if(!previousTasks.contains(previousTask.id())) {
			previousTasks.addItem(previousTask.id());
		}
	}

	public void addSubTask(Task task) {
		T.call(this);
		
		if(!subTasks.contains(task.id())) {
			subTasks.addItem(task.id());
		}
	}
	
	public void addNextTask(Task nextTask) {
		T.call(this);
		
		if(!nextTasks.contains(nextTask.id())) {
			nextTasks.addItem(nextTask.id());
		}
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
		if(parent != null) {
			parent.forEachSubTask(sib -> {
				if(sib != this) {
					lambda.execute(sib);
				}
			});
		}
	}


	private List<Task> trunk() {
		
		T.call(this);
		
		List<Task> trunk = null;
		Task parent = parent();
		
		if(parent != null) {

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

	public String name() {
		return getPath().lastName();
	}

	public void forEachStartTaskLocal(TaskLambda lambda) {
		T.call(this);

		for(String subTaskId : getSubTasks().getValue()) {
			Task subTask = graph.findTaskByPath(new Path(subTaskId));
			if(subTask.isStartTaskLocal()) {
				lambda.execute(subTask);
			}
		}
	}

	public void forEachReachableTaskLocal(TaskLambda lambda) {
		T.call(this);
		
		forEachReachableTaskLocalImpl(parent(), new HashSet<>(), lambda);
	}

	public void forEachReachableTaskLocalImpl(Task parent,
			                                  Set<Task> visitedTasks,
			                                  TaskLambda lambda) {
		T.call(this);
		
		if(Ntro.collections().setContainsExact(visitedTasks, this)) return;
		visitedTasks.add(this);

		for(String nextTaskId : getNextTasks().getValue()) {
			Task nextTask = graph.findTaskByPath(new Path(nextTaskId));
			
			if(nextTask.parent() == parent) {
				lambda.execute(nextTask);
				nextTask.forEachReachableTaskLocalImpl(parent, 
						                               visitedTasks, 
						                               lambda);
			}
		}
	}


	public boolean isStartTaskLocal() {
		boolean isStartTaskLocal = true;

		for(String previousTaskId : getPreviousTasks().getValue()) {
			Task previousTask = graph.findTaskByPath(new Path(previousTaskId));
			if(previousTask.parent() == parent()) {
				isStartTaskLocal = false;
				break;
			}
		}

		return isStartTaskLocal;
	}


	public void forEachSubTaskInOrder(TaskLambda lambda) {
		T.call(this);
		
		Set<Task> visitedTasks = new HashSet<>();
		
		forEachStartTaskLocal(st -> {

			if(Ntro.collections().setContainsExact(visitedTasks, st)) return;
			visitedTasks.add(st);
			
			lambda.execute(st);
			
			st.forEachReachableTaskLocal(rt -> {

				if(Ntro.collections().setContainsExact(visitedTasks, rt)) return;
				visitedTasks.add(rt);
				
				lambda.execute(rt);
			});
		});
	}

	public void forEachPreviousTaskInOrder(TaskLambda lambda) {
		T.call(this);
		
		// TODO: order according to the graph
		forEachPreviousTask(lambda);
	}

	public void forEachNextTaskInOrder(TaskLambda lambda) {
		T.call(this);
		
		// TODO: order according to the graph
		forEachNextTask(lambda);
	}

	public void removeTask(String taskId) {
		T.call(this);
		
		removePreviousTask(taskId);
		removeSubTask(taskId);
		removeNextTask(taskId);
	}

	public void removePreviousTask(String taskId) {
		T.call(this);
		
		previousTasks.removeItem(taskId);
	}

	public void removeSubTask(String taskId) {
		T.call(this);
		
		subTasks.removeItem(taskId);
	}

	public void removeNextTask(String taskId) {
		T.call(this);

		nextTasks.removeItem(taskId);
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

}
