package ca.aquiletour.core.pages.course.models;

import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class CourseModel implements NtroModel, TaskGraph {
	
	private ObservableTaskMap tasks = new ObservableTaskMap();
	
	public Task findTaskByPath(Path path) {
		T.call(this);
		
		String taskId = path.toString();

		return findTaskById(taskId);
	}

	public Task findTaskById(String taskId) {
		T.call(this);
		
		return tasks.valueOf(taskId);
	}

	public ObservableTaskMap getTasks() {
		return tasks;
	}

	public void setTasks(ObservableTaskMap tasks) {
		this.tasks = tasks;
	}


	public void addTask(Task task) {
		T.call(this);
		TaskNode taskNode = asGraph().findNodeByPath(task.getTaskPath());
		TaskNode parentNode = taskNode.getParent();
		
		if(parentNode != null) {
			parentNode.asTask().addSubTask(task);
		}
		
		
		
		Path parentTaskPath = task.parentTaskPath();
		
		Task parent = findTaskByPath(parentTaskPath);
		
		if(parent != null) {
			parent.addSubTask(task);
		}

		tasks.addEntry(task.getTaskPath().toString(), task);
	}

	public TaskBreadcrumbs breadcrumbsForTask(Task task) {
		T.call(this);
		
		TaskBreadcrumbs breadcrumbs = new TaskBreadcrumbs();
		
		breadcrumbs.setTrunk(task.getTaskPath());
		
		breadcrumbs.addBranches(asGraph());

		return breadcrumbs;
	}
	
	public TaskGraph asGraph() {
		T.call(this);

		return this;
	}

}
