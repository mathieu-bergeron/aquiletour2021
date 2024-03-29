package ca.aquiletour.core.models.courses.base;

import java.util.List;
import java.util.Map;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;
import ca.aquiletour.core.models.courses.base.lambdas.TaskForEach;
import ca.aquiletour.core.models.courses.student.CompletionByAtomicTaskId;
import ca.aquiletour.core.models.courses.student.StudentCompletionsByTaskId;
import ca.aquiletour.core.models.courses.teacher.CourseIds;
import ca.aquiletour.core.models.dates.AquiletourDate;
import ca.aquiletour.core.models.dates.CourseDate;
import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.models.paths.TaskPath;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.pages.course_list.models.SemesterIds;
import ca.aquiletour.core.pages.dashboard.models.CurrentTask;
import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.lambdas.Break;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

public abstract class CourseModel<CT extends CurrentTask> implements NtroModel, TaskGraph {

	private CoursePath coursePath = new CoursePath();
	private SemesterIds siblingSemesters = new SemesterIds();
	private CourseIds siblingCourses = new CourseIds();

	private ObservableTaskMap tasks = new ObservableTaskMap(this);
	
	public SemesterIds getSiblingSemesters() {
		return siblingSemesters;
	}

	public void setSiblingSemesters(SemesterIds siblingSemesters) {
		this.siblingSemesters = siblingSemesters;
	}

	public CourseIds getSiblingCourses() {
		return siblingCourses;
	}

	public void setSiblingCourses(CourseIds siblingCourses) {
		this.siblingCourses = siblingCourses;
	}

	@Override
	public Task findTaskByPath(Path path) {
		T.call(this);
		
		return findTaskById(pathToId(path));
	}

	protected String pathToId(Path path) {
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

	public void registerRootTask(Task rootTask) {
		T.call(this);
		
		rootTask.setGraph(asGraph());

		tasks.putEntry(rootTask.id(), rootTask);
	}

	public void addSubTaskTo(Path parentPath, Task subTask) {
		T.call(this);
		
		subTask.setGraph(asGraph());

		Task parent = findTaskByPath(parentPath);

		if(parent != null) {
			
			addNewTask(subTask);

			parent.addSubTask(subTask);

		}else {
			Log.warning("parentTask not found: " + parentPath);
		}
	}

	public void addNextTaskTo(Path previousPath, Task nextTask) throws CycleDetectedError {
		T.call(this);
		
		nextTask.setGraph(asGraph());

		Task previousTask = findTaskByPath(previousPath);

		if(previousTask != null) {
			
			Task existingNextTask = findTaskByPath(nextTask.getPath());
			
			if(existingNextTask != null) {
				
				previousTask.addNextTask(existingNextTask);
				existingNextTask.addPreviousTask(previousTask);
				
				GraphPath cycle = rootTask().findFirstCycle();
				if(cycle != null) {
					previousTask.removeNextTask(existingNextTask.id());
					existingNextTask.removePreviousTask(previousTask.id());
					throw new CycleDetectedError();
				}
				
			}else {

				addNewNextTask(previousTask, nextTask);

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

	private void addNewTask(Task newTask) {
		T.call(this);
		
		tasks.putEntry(newTask.id(), newTask);
	}

	public void addPreviousTaskTo(Path path, Task previousTask) throws CycleDetectedError {
		T.call(this);
		
		previousTask.setGraph(asGraph());

		Task nextTask = findTaskByPath(path);

		if(nextTask != null) {
			
			Task existingPreviousTask = findTaskByPath(previousTask.getPath());
			
			if(existingPreviousTask != null) {
				
				nextTask.addPreviousTask(existingPreviousTask);
				existingPreviousTask.addNextTask(nextTask);

				GraphPath cycle = rootTask().findFirstCycle();
				if(cycle != null) {
					nextTask.removePreviousTask(existingPreviousTask.id());
					existingPreviousTask.removeNextTask(nextTask.id());
					throw new CycleDetectedError();
				}
				
			}else {

				addNewPreviousTask(previousTask, nextTask);

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

	
	public CoursePath getCoursePath() {
		return coursePath;
	}

	public void setCoursePath(CoursePath coursePath) {
		this.coursePath = coursePath;
	}

	public void forEachTask(TaskForEach lambda) {
		T.call(this);
		
		for(Task task : tasks.getValue().values()) {
			try {
				
				lambda.execute(task);
				
			}catch(Break b) {
				break;
			}
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
	
	public String courseId() {
		T.call(this);
		return coursePath.toFileName();
	}
	
	
	public void updateCourseTitle(String courseTitle) {
		T.call(this);

		rootTask().updateTitle(courseTitle);
	}

	protected Task rootTask() {
		T.call(this);

		Task rootTask = findTaskById("/");
		
		if(rootTask == null) {
			Log.warning("rootTask should always exists");
		}

		return rootTask;
	}

	public void updateTaskInfo(Path taskPath, 
			                   String taskTitle, 
			                   String taskDescription, 
			                   AquiletourDate endTime,
			                   OnAtomicTaskAdded atomicTaskListener) {
		T.call(this);

		Task task = findTaskByPath(taskPath);
		
		if(task != null) {
			
			task.updateTitle(taskTitle);
			task.updateDescription(taskDescription, atomicTaskListener);
			task.updateEndTime(endTime);
			
		}else {
			
			Log.warning("Task not found: " + taskPath.toString());
		}
	}

	public AquiletourDate taskEndTimeForGroup(String groupId, String taskId) {
		T.call(this);
		
		AquiletourDate endTime = null;

		Task task = findTaskById(taskId);
		
		if(task != null) {
			
			endTime = task.getEndTime().getValue();

		}else {
			
			Log.warning("Task not found: " + taskId);
		}
		
		return endTime;
	}

	public void updateSchedule(SemesterSchedule semesterSchedule) {
		T.call(this);
		
		for(Task task : tasks.getValue().values()) {
			task.updateDate(semesterSchedule);
		}
	}

	public void updateSchedule(SemesterSchedule semesterSchedule, TeacherSchedule teacherSchedule) {
		T.call(this);
		
		updateSchedule(semesterSchedule);
		updateSchedules(semesterSchedule, teacherSchedule);
	}

	protected abstract void updateSchedules(SemesterSchedule semesterSchedule, TeacherSchedule teacherSchedule);
	
	private void copyTasks(CourseModel<?> course) {
		T.call(this);
		
		getTasks().setCourse(this);
		for(Map.Entry<String, Task> entry : course.getTasks().getValue().entrySet()) {
			Task newTask = new Task();
			getTasks().putEntry(entry.getKey(), newTask);
			
			newTask.copyTask(entry.getValue());
		}
	}

	public void copyCourse(CourseModel<?> course) {
		T.call(this);
		
		coursePath = course.getCoursePath();
		copyTasks(course);
	}

	protected void updateAtomicTaskCompletion(StudentCompletionsByTaskId studentCompletions, 
			                                  Path taskPath,
			                                  String atomicTaskId,
			                                  AtomicTaskCompletion newCompletion) {
		T.call(this);

		CompletionByAtomicTaskId taskCompletions = studentCompletions.valueOf(taskPath.toKey());
		if(taskCompletions == null) {
			taskCompletions = new CompletionByAtomicTaskId();
			studentCompletions.putEntry(taskPath.toKey(), taskCompletions);
		}

		taskCompletions.putEntry(atomicTaskId, newCompletion);
	}

	protected void removeAtomicTaskCompletion(StudentCompletionsByTaskId studentCompletions, 
			                                  Path taskPath,
			                                  String atomicTaskId) {
		T.call(this);

		CompletionByAtomicTaskId taskCompletions = studentCompletions.valueOf(taskPath.toKey());
		if(taskCompletions != null) {
			taskCompletions.removeEntry(atomicTaskId);
		}
	}

	protected AtomicTaskCompletion atomicTaskCompletion(StudentCompletionsByTaskId studentCompletions, 
			                                            Path taskPath,
			                                            String atomicTaskId) {
		T.call(this);
		
		AtomicTaskCompletion result = null;

		CompletionByAtomicTaskId taskCompletions = studentCompletions.valueOf(taskPath.toKey());
		if(taskCompletions != null) {
			result = taskCompletions.valueOf(atomicTaskId);
		}
		
		return result;
	}

	public AtomicTask atomicTask(TaskPath taskPath, String atomicTaskId) {
		T.call(this);
		
		AtomicTask atomicTask = null;
		
		Task task = findTaskByPath(taskPath);
		if(task != null) {
			atomicTask = task.atomicTaskById(atomicTaskId);
		}
		
		return atomicTask;
	}

	public abstract List<CT> currentTasks();

}
