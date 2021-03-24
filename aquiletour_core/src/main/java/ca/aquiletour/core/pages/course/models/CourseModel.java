package ca.aquiletour.core.pages.course.models;

import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class CourseModel implements NtroModel {
	
	private ObservableTaskMap tasks = new ObservableTaskMap();
	
	public Task findTaskByPath(Path path) {
		T.call(this);
		
		String taskId = path.toString();

		return findTaskById(taskId);
	}

	public Task findTaskById(String taskId) {
		T.call(this);
		
		System.out.println("findTaskById: " + taskId);

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
		
		System.out.println("addTask " + task.getTaskPath().toString());
		
		Path parentTaskPath = task.parentTaskPath();

		System.out.println("parentTaskPath " + parentTaskPath.toString());
		
		Task parent = findTaskByPath(parentTaskPath);
		
		if(parent != null) {
			parent.addSubTask(task);
		}

		tasks.addEntry(task.getTaskPath().toString(), task);
	}

}
