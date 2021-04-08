package ca.aquiletour.core.models.courses.base;

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

	public void addSubTaskTo(Path parentPath, Task subTask, OnTaskAdded onTaskAdded) {
		T.call(this);
		
		subTask.setGraph(asGraph());

		Task parent = findTaskByPath(parentPath);

		if(parent != null) {
			
			addNewTask(subTask);

			parent.addSubTask(subTask);
			
			onTaskAdded.onTaskAdded();

		}else {
			Log.warning("parentTask not found: " + parentPath);
		}
	}

	public void addNextTaskTo(Path previousPath, Task nextTask, OnTaskAdded onTaskAdded) {
		T.call(this);
		
		nextTask.setGraph(asGraph());

		Task previousTask = findTaskByPath(previousPath);

		if(previousTask != null) {
			
			Task existingNextTask = findTaskByPath(nextTask.getPath());
			
			if(existingNextTask != null) {
				
				previousTask.addNextTask(existingNextTask);
				existingNextTask.addPreviousTask(previousTask);
				
			}else {

				addNewNextTask(previousTask, nextTask);

				onTaskAdded.onTaskAdded();
			}
			
		}else {
			
			Log.warning("task not found: " + previousPath);
		}
	}

	private void addNewNextTask(Task previousTask, Task nextTask) {
		T.call(this);

		// XXX: addNewTask first
		addNewTask(nextTask);

		previousTask.addNextTask(nextTask);

		nextTask.addPreviousTask(previousTask);

		Task parent = previousTask.parent();
		if(parent != null) {
			parent.addSubTask(nextTask);
		}
	}

	private void addNewTask(Task nextTask) {
		T.call(this);

		tasks.addEntry(nextTask.id(), nextTask);
		Ntro.modelStore().updateStoreConnections(this);
	}

	public void addPreviousTaskTo(Path path, Task previousTask, OnTaskAdded onTaskAdded) {
		T.call(this);
		
		previousTask.setGraph(asGraph());

		Task nextTask = findTaskByPath(path);

		if(nextTask != null) {
			
			Task existingPreviousTask = findTaskByPath(previousTask.getPath());
			
			if(existingPreviousTask != null) {
				
				nextTask.addPreviousTask(existingPreviousTask);
				existingPreviousTask.addNextTask(nextTask);
				
			}else {

				addNewPreviousTask(previousTask, nextTask);

				onTaskAdded.onTaskAdded();
			}

		}else {
			
			Log.warning("task not found: " + path);
		}
	}

	private void addNewPreviousTask(Task previousTask, Task nextTask) {
		addNewTask(previousTask);

		// XXX: now we can modify the new task
		//      (its stored properties are now connected)
		previousTask.addNextTask(nextTask);

		nextTask.addPreviousTask(previousTask);
		
		Task parent = nextTask.parent();
		if(parent != null) {
			parent.addSubTask(previousTask);
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

	public void deleteTask(Path taskToDelete, OnTaskRemoved onTaskRemoved) {
		T.call(this);
		
		deleteTask(pathToId(taskToDelete), onTaskRemoved);
	}

	public void deleteTask(String taskId, OnTaskRemoved onTaskRemoved) {
		T.call(this);
		
		if(tasks.containsKey(taskId)) {

			forEachTask(t -> t.removeTask(taskId));
			
			Task task = tasks.valueOf(taskId);

			tasks.removeEntry(taskId);

			onTaskRemoved.onTaskRemoved(task);
		}
		
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
