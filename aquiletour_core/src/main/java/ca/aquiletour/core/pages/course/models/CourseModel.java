package ca.aquiletour.core.pages.course.models;

import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

public class CourseModel implements NtroModel, TaskGraph {

	private String courseId = "";
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

	public void setRootTask(Task rootTask) {
		T.call(this);
		
		rootTask.setGraph(asGraph());

		tasks.addEntry(rootTask.id(), rootTask);
	}

	public void addSubTaskTo(Path parentPath, Task subTask) {
		T.call(this);
		
		subTask.setGraph(asGraph());

		Task parent = findTaskByPath(parentPath);

		if(parent != null) {

			parent.addSubTask(subTask);
			tasks.addEntry(subTask.id(), subTask);

		}else {
			Log.warning("parentTask not found: " + parentPath);
		}
	}

	public void addNextTaskTo(Path path, Task nextTask) {
		T.call(this);
		
		nextTask.setGraph(asGraph());

		Task task = findTaskByPath(path);

		if(task != null) {
			
			task.addNextTask(nextTask);
			if(!tasks.containsKey(nextTask.id())) {
				tasks.addEntry(nextTask.id(), nextTask);
			}

		}else {
			
			Log.warning("task not found: " + path);
		}
	}
	
	public TaskGraph asGraph() {
		T.call(this);

		return this;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
}
