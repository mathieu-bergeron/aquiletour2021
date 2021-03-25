package ca.aquiletour.core.pages.course.models;

import ca.ntro.core.Path;
import ca.ntro.core.models.ModelFactory;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

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

	public void addNextTaskTo(Path previousPath, Task nextTask) {
		T.call(this);
		
		nextTask.setGraph(asGraph());

		Task previousTask = findTaskByPath(previousPath);

		if(previousTask != null) {
			
			Task existingNextTask = findTaskByPath(nextTask.getPath());
			
			if(existingNextTask != null) {
				
				previousTask.addNextTask(existingNextTask);
				existingNextTask.addPreviousTask(previousTask);
				
			}else {

				previousTask.addNextTask(nextTask);

				tasks.addEntry(nextTask.id(), nextTask);
				Ntro.modelStore().updateStoreConnections(this);

				nextTask.addPreviousTask(previousTask);
			}
			
		}else {
			
			Log.warning("task not found: " + previousPath);
		}
	}

	public void addPreviousTaskTo(Path path, Task previousTask) {
		T.call(this);
		
		previousTask.setGraph(asGraph());

		Task nextTask = findTaskByPath(path);

		if(nextTask != null) {
			
			Task existingPreviousTask = findTaskByPath(previousTask.getPath());
			
			if(existingPreviousTask != null) {
				
				nextTask.addPreviousTask(existingPreviousTask);
				existingPreviousTask.addNextTask(nextTask);
				
			}else {

				nextTask.addPreviousTask(previousTask);
				
				Task parent = nextTask.parent();
				if(parent != null) {
					parent.addSubTask(previousTask);
				}
				
				// XXX: before modifying the new task, we must:
				//      - insert it in the model
				//      - update the model store connections
				tasks.addEntry(previousTask.id(), previousTask);
				Ntro.modelStore().updateStoreConnections(this);
				
				// XXX: now we can modify the new task
				//      (its stored properties are now connected)
				previousTask.addNextTask(nextTask);
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
