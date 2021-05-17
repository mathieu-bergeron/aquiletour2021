package ca.aquiletour.core.models.courses.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.aquiletour.core.models.dates.CourseDate;
import ca.aquiletour.core.models.dates.SemesterDate;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class Task implements NtroModelValue, TaskNode {
	
	private TaskGraph graph;

	private Path path = new Path();

	private StoredString title = new StoredString();
	private StoredString description = new StoredString();
	private StoredCourseDate endTime = new StoredCourseDate();

	private StoredAtomicTasks entryTasks = new StoredAtomicTasks();
	private StoredAtomicTasks exitTasks = new StoredAtomicTasks();
	
	private StoredTaskIds previousTasks = new StoredTaskIds();
	private StoredTaskIds subTasks = new StoredTaskIds();
	private StoredTaskIds nextTasks = new StoredTaskIds();

	public void copyTask(Task task) {
		T.call(this);

		updateTitle(task.getTitle().getValue());
		updateDescription(task.getDescription().getValue());
		updateEndTime(task.getEndTime().getValue());
		
		copyTaskIds(task.getPreviousTasks(), previousTasks);
		copyTaskIds(task.getSubTasks(), subTasks);
		copyTaskIds(task.getNextTasks(), nextTasks);
	}
	
	private void copyTaskIds(StoredTaskIds from, StoredTaskIds to) {
		T.call(this);
		
		for(String taskId : from.getValue()) {
			to.addItem(taskId);
		}
	}

	public StoredTaskIds getPreviousTasks() {
		return previousTasks;
	}

	public void setPreviousTasks(StoredTaskIds previousTasks) {
		this.previousTasks = previousTasks;
	}

	public StoredTaskIds getSubTasks() {
		return subTasks;
	}

	public void setSubTasks(StoredTaskIds subTasks) {
		this.subTasks = subTasks;
	}

	public StoredTaskIds getNextTasks() {
		return nextTasks;
	}

	public void setNextTasks(StoredTaskIds nextTasks) {
		this.nextTasks = nextTasks;
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


	public boolean isRootTask() {
		return parent() == null;
	}

	public StoredString getTitle() {
		return title;
	}

	public void setTitle(StoredString title) {
		this.title = title;
	}

	public StoredString getDescription() {
		return description;
	}

	public void setDescription(StoredString description) {
		this.description = description;
	}

	public void updateDescription(String description) {
		T.call(this);
		
		getDescription().set(description);
		
		AtomicTask.addAtomicTasksFromDescription(this, description);
	}
	
	public void deleteAtomicTask(String taskId) {
		T.call(this);
		
	}
	
	public void updateTitle(String title) {
		T.call(this);
		
		getTitle().set(title);
	}
	
	public void updateEndTime(CourseDate endTime) {
		T.call(this);
		
		getEndTime().set(endTime);
	}

	public StoredCourseDate getEndTime() {
		return endTime;
	}

	public void setEndTime(StoredCourseDate endTime) {
		this.endTime = endTime;
	}
	
	public StoredAtomicTasks getEntryTasks() {
		return entryTasks;
	}

	public void setEntryTasks(StoredAtomicTasks entryTasks) {
		this.entryTasks = entryTasks;
	}

	public StoredAtomicTasks getExitTasks() {
		return exitTasks;
	}

	public void setExitTasks(StoredAtomicTasks exitTasks) {
		this.exitTasks = exitTasks;
	}

	public void updateDate(SemesterSchedule semesterSchedule) {
		T.call(this);
		
		CourseDate endTimeDate = endTime.getValue();
		
		boolean updated = endTimeDate.updateDate(semesterSchedule);
		
		if(updated) {
			endTime.set(endTimeDate);
		}
	}

	public SemesterDate resolveDate(String courseId, 
			                        String groupId, 
			                        SemesterSchedule semesterSchedule, 
			                        TeacherSchedule teacherSchedule) {
		T.call(this);
		
		SemesterDate date = null;

		CourseDate endTimeDate = endTime.getValue();
		
		date = endTimeDate.resolveDate(courseId, groupId, semesterSchedule, teacherSchedule);

		return date;
	}

	public void addEntryTask(AtomicTask task) {
		T.call(this);
		
		if(!getEntryTasks().contains(task)) {
			task.setId(nextAtomicTaskId());
			getEntryTasks().addItem(task);
		}
	}

	public void addExitTask(AtomicTask task) {
		T.call(this);

		if(!getExitTasks().contains(task)) {
			task.setId(nextAtomicTaskId());
			getExitTasks().addItem(task);
		}
	}
	
	private AtomicTask atomicTaskById(String id) {
		T.call(this);
		
		AtomicTask task = null;
		
		task = atomicTaskById(id, entryTasks);
		
		if(task == null) {
			
			task = atomicTaskById(id, exitTasks);
		}
		
		return task;
	}

	private AtomicTask atomicTaskById(String id, StoredAtomicTasks tasks) {
		T.call(this);
		
		AtomicTask task = null;
		
		for(AtomicTask candidate : tasks.getValue()) {
			if(id.equals(candidate.getId())) {
				task = candidate;
				break;
			}
		}
		
		return task;
	}
	
	private String nextAtomicTaskId() {
		T.call(this);
		
		return String.valueOf(getEntryTasks().size() + getExitTasks().size());
	}

}
