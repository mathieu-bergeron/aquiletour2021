package ca.aquiletour.core.pages.course.models;

import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class CourseModel implements NtroModel, TaskGraph {

	private ObservableTaskMap tasks = new ObservableTaskMap(this);

	@Override
	public Task findTaskByPath(Path path) {
		T.call(this);
		
		String taskId = path.toString();

		return findTaskById(taskId);
	}

	private Task findTaskById(String taskId) {
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
		
		task.setGraph(asGraph());

		Task parent = task.parent();

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
