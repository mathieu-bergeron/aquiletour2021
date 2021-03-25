package ca.aquiletour.core.pages.course.models;

import ca.ntro.core.Path;
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
		
		return findTaskById(pathToId(path));
	}

	private String pathToId(Path path) {
		return path.toString();
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

			// XXX: must call this before
			//      as addSubTask triggers a re-display
			tasks.addEntry(subTask.id(), subTask);

			parent.addSubTask(subTask);

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


				// XXX: addEntry must be called before anyting else
				//      otherwise the task does not exists
				//      and refering to it by id will fail
				tasks.addEntry(nextTask.id(), nextTask);
				Ntro.modelStore().updateStoreConnections(this);

				previousTask.addNextTask(nextTask);

				nextTask.addPreviousTask(previousTask);

				Task parent = previousTask.parent();
				if(parent != null) {
					parent.addSubTask(nextTask);
				}
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

				
				// XXX: before modifying the new task, we must:
				//      - insert it in the model
				//      - update the model store connections
				tasks.addEntry(previousTask.id(), previousTask);
				Ntro.modelStore().updateStoreConnections(this);

				// XXX: now we can modify the new task
				//      (its stored properties are now connected)
				previousTask.addNextTask(nextTask);

				nextTask.addPreviousTask(previousTask);
				
				Task parent = nextTask.parent();
				if(parent != null) {
					parent.addSubTask(previousTask);
				}
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
	
	public void forEachTask(TaskLambda lambda) {
		T.call(this);
		
		for(Task task : tasks.getValue().values()) {
			lambda.execute(task);
		}
	}

	public void deleteTask(Path taskToDelete) {
		T.call(this);
		
		deleteTask(pathToId(taskToDelete));
	}

	public void deleteTask(String taskId) {
		T.call(this);
		
		forEachTask(t -> t.removeTask(taskId));
		
		tasks.removeEntry(taskId);
		
		// FIXME: remove unreachable tasks!
	}

	public void removePreviousTask(Path taskToModify, Path taskToDelete) {
		T.call(this);
		
		Task toModify = findTaskByPath(taskToModify);
		
		toModify.removePreviousTask(pathToId(taskToDelete));
		
		// FIXME: remove unreachable tasks!
	}

	public void removeSubTask(Path taskToModify, Path taskToDelete) {
		T.call(this);
		
		Task toModify = findTaskByPath(taskToModify);
		
		toModify.removeSubTask(pathToId(taskToDelete));
		
		// FIXME: remove unreachable tasks!
	}

	public void removeNextTask(Path taskToModify, Path taskToDelete) {
		T.call(this);
		
		Task toModify = findTaskByPath(taskToModify);
		
		toModify.removeNextTask(pathToId(taskToDelete));
		
		// FIXME: remove unreachable tasks!
	}
	
}
