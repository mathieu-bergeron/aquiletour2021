package ca.aquiletour.core.models.courses.base;

import static ca.aquiletour.core.models.courses.base.lambdas.VisitDirection.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;
import ca.aquiletour.core.models.courses.base.lambdas.FindResult;
import ca.aquiletour.core.models.courses.base.lambdas.FindResults;
import ca.aquiletour.core.models.courses.base.lambdas.TaskForEach;
import ca.aquiletour.core.models.courses.base.lambdas.TaskMatcher;
import ca.aquiletour.core.models.courses.base.lambdas.TaskReducer;
import ca.aquiletour.core.models.courses.base.lambdas.VisitDirection;
import ca.aquiletour.core.models.courses.status.BlockedWaitingForParent;
import ca.aquiletour.core.models.courses.status.BlockedWaitingForPreviousTasks;
import ca.aquiletour.core.models.courses.status.BlockedWaitingForSubTasks;
import ca.aquiletour.core.models.courses.status.StatusDone;
import ca.aquiletour.core.models.courses.status.StatusTodo;
import ca.aquiletour.core.models.courses.status.TaskStatus;
import ca.aquiletour.core.models.courses.student.CompletionByAtomicTaskId;
import ca.aquiletour.core.models.courses.student.StudentCompletionsByTaskId;
import ca.aquiletour.core.models.dates.CourseDate;
import ca.aquiletour.core.models.dates.SemesterDate;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.models.functionnal.Break;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class Task implements NtroModelValue, TaskNode {
	
	private TaskGraph graph;

	private TaskPath path = new TaskPath();

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
		
		setPath(task.getPath());

		updateTitle(task.getTitle().getValue());
		updateDescription(task.getDescription().getValue(), new OnAtomicTaskAdded() {
			@Override
			public void onAtomicTaskAdded(Task task, AtomicTask atomicTask) {
				T.call(this);
			}
		});
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

	public TaskPath getPath() {
		return path;
	}

	public void setPath(TaskPath taskPath) {
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
	
	public void forEachSubTask(TaskForEach lambda) {
		T.call(this);
		
		reduceTo(Void.class, new VisitDirection[]{SUB}, false, null, (distance, task, accumulatorArg) ->{
			lambda.execute(task);
			return accumulatorArg;
		});
	}

	public void forEachNextTask(TaskForEach lambda) {
		T.call(this);

		reduceTo(Void.class, new VisitDirection[]{NEXT}, false, null, (distance, task, accumulatorArg) ->{
			lambda.execute(task);
			return accumulatorArg;
		});
	}

	public void forEachPreviousTask(TaskForEach lambda) {
		T.call(this);

		reduceTo(Void.class, new VisitDirection[]{PREVIOUS}, false, null, (distance, task, accumulatorArg) ->{
			lambda.execute(task);
			return accumulatorArg;
		});
	}

	public void forEachSibling(TaskForEach lambda) {
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
		return idFromPath(getPath());
	}
	
	public static String idFromPath(Path taskPath) {
		T.call(Task.class);
		
		return taskPath.toString();
	}

	public void forEachStartTaskLocal(TaskForEach lambda) {
		T.call(this);

		for(String subTaskId : getSubTasks().getValue()) {
			Task subTask = graph.findTaskByPath(new Path(subTaskId));
			if(subTask.isStartTaskLocal()) {
				try {

					lambda.execute(subTask);

				}catch(Break b) {
					break;
				}
			}
		}
	}

	public void forEachReachableTaskLocal(TaskForEach lambda) {
		T.call(this);
		
		forEachReachableTaskLocalImpl(parent(), new HashSet<>(), lambda);
	}

	public void forEachReachableTaskLocalImpl(Task parent,
			                                  Set<Task> visitedTasks,
			                                  TaskForEach lambda) {
		T.call(this);
		
		if(Ntro.collections().setContainsExact(visitedTasks, this)) return;
		visitedTasks.add(this);

		for(String nextTaskId : getNextTasks().getValue()) {
			Task nextTask = graph.findTaskByPath(new Path(nextTaskId));
			
			if(nextTask.parent() == parent) {
				try {

					lambda.execute(nextTask);

				}catch(Break b) {
					break;
				}

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

	public void forEachSubTaskInOrder(TaskForEach lambda) {
		T.call(this);
		
		FindResults findResults = getSubTasks().reduceTo(FindResults.class, new FindResults(), (index, subTaskId, accumulator) -> {
			Task subTask = graph.findTaskByPath(new Path(subTaskId));
			
			FindResult distanceToSubTask = distanceToTask(new VisitDirection[] {SUB, NEXT}, subTask);
			
			accumulator.addOrUpdateFindResult(distanceToSubTask);
			
			return accumulator;
		});
		
		findResults.asList().sort((findResult1, findResult2) -> {
			return Integer.compare(findResult1.getMaxDistance(), findResult2.getMaxDistance());
		});
		
		findResults.forEachTask(lambda);
	}

	private FindResult distanceToTask(Task task) {
		T.call(this);

		return distanceToTask(new VisitDirection[] {PARENT, SUB, PREVIOUS, NEXT}, task);
	}
	
	private FindResult distanceToTask(VisitDirection[] howToVisitTasks, Task task) {
		T.call(this);
		
		FindResult result = null;
		
		FindResults findResults = findAll(howToVisitTasks, true, visitedTask -> {
			return visitedTask == task;
		});
		
		if(findResults.size() == 1) {

			result = findResults.get(0);
			
		}else {
			
			Log.warning("Should not find more than one task");
		}

		return result;
	}

	public void forEachPreviousTaskInOrder(TaskForEach lambda) {
		T.call(this);

		FindResults findResults = getPreviousTasks().reduceTo(FindResults.class, new FindResults(), (index, previousTaskId, accumulator) -> {
			Task previousTask = graph.findTaskByPath(new Path(previousTaskId));
			
			FindResult distanceToPreviousTask = distanceToTask(new VisitDirection[] {PREVIOUS}, previousTask);
			
			accumulator.addOrUpdateFindResult(distanceToPreviousTask);
			
			return accumulator;
		});
		
		findResults.asList().sort((findResult1, findResult2) -> {
			return Integer.compare(findResult1.getMaxDistance(), findResult2.getMaxDistance());
		});
		
		findResults.forEachTask(lambda);
	}

	public void forEachNextTaskInOrder(TaskForEach lambda) {
		T.call(this);

		FindResults findResults = getNextTasks().reduceTo(FindResults.class, new FindResults(), (index, nextTaskId, accumulator) -> {
			Task nextTask = graph.findTaskByPath(new Path(nextTaskId));
			
			FindResult distanceToNextTask = distanceToTask(new VisitDirection[] {NEXT}, nextTask);
			
			accumulator.addOrUpdateFindResult(distanceToNextTask);
			
			return accumulator;
		});
		
		findResults.asList().sort((findResult1, findResult2) -> {
			return Integer.compare(findResult1.getMaxDistance(), findResult2.getMaxDistance());
		});

		findResults.forEachTask(lambda);
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
		return !hasParent();
	}

	public boolean hasParent() {
		return parent() !=  null;
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

	public void updateDescription(String description, OnAtomicTaskAdded atomicTaskListener) {
		T.call(this);
		
		getDescription().set(description);
		
		AtomicTask.addAtomicTasksFromDescription(this, description, atomicTaskListener);
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

	public void addEntryTask(AtomicTask atomicTask, OnAtomicTaskAdded atomicTaskListener) {
		T.call(this);

		addAtomicTask(atomicTask, atomicTaskListener, getEntryTasks());
	}

	public void addExitTask(AtomicTask atomicTask, OnAtomicTaskAdded atomicTaskListener) {
		T.call(this);

		addAtomicTask(atomicTask, atomicTaskListener, getExitTasks());
	}
	
	private void addAtomicTask(AtomicTask atomicTask, 
							   OnAtomicTaskAdded atomicTaskListener, 
							   StoredAtomicTasks storedTasks) {
		T.call(this);

		AtomicTask existingTask = atomicTaskByType(atomicTask.getClass(), storedTasks);
		if(existingTask == null) {
			storedTasks.addItem(atomicTask);
			atomicTaskListener.onAtomicTaskAdded(this, atomicTask);
		}
	}

	private AtomicTask atomicTaskByType(Class<? extends AtomicTask> taskType,
							            StoredAtomicTasks storedTasks) {
		T.call(this);
		
		return storedTasks.findFirst(AtomicTask.class, (index, task) -> {
			return task.getClass().equals(taskType);
		});
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

	public void forEachTaskBackwardsTransitive(TaskForEach lambda) {
		T.call(this);

		if(!isRootTask()) {
			parent().forEachTaskBackwardsTransitive(lambda);
		}
		
		forEachPreviousTask(pt -> {
			pt.forEachTaskBackwardsTransitive(lambda);
		});
	}

	public <R extends Object> R reduceTo(Class<R> resultClass, 
			                             VisitDirection[] howToVisitNodes, 
										 boolean transitive,
										 R accumulator,
			                             TaskReducer<R> reducer) {
		
		Set<String> visistedNodes = new HashSet<>();
		visistedNodes.add(this.id());
		
		try {
			accumulator = reducer.reduce(0, this, accumulator);
		} catch(Break b) {}
		
		return reduceToImpl(resultClass, howToVisitNodes, transitive, accumulator, reducer, 0, visistedNodes);
	}

	private <R extends Object> R reduceToImpl(Class<R> resultClass, 
			                                  VisitDirection[] howToVisitNodes, 
										      boolean transitive,
										      R accumulator,
			                                  TaskReducer<R> reducer,
			                                  int distance,
			                                  Set<String> visitedNodes) {
		T.call(this);
		
		for(VisitDirection direction : howToVisitNodes) {
			switch(direction) {
				case PREVIOUS:
					accumulator = reduceToForTasksToVisit(resultClass, 
							                              howToVisitNodes, 
							                              transitive, 
							                              accumulator, 
							                              reducer, 
							                              distance+1, 
							                              visitedNodes,
							                              getPreviousTasks());
					break;

				case SUB:
					accumulator = reduceToForTasksToVisit(resultClass, 
							                              howToVisitNodes, 
							                              transitive, 
							                              accumulator, 
							                              reducer, 
							                              distance+1, 
							                              visitedNodes, 
							                              getSubTasks());
					break;

				case NEXT:
					accumulator = reduceToForTasksToVisit(resultClass, 
							                              howToVisitNodes, 
							                              transitive, 
							                              accumulator, 
							                              reducer, 
							                              distance+1, 
							                              visitedNodes, 
							                              getNextTasks());
					break;

				case PARENT:
					if(!isRootTask()) {
						accumulator = reduceToForTaskToVisit(resultClass, 
								                             howToVisitNodes, 
											                 transitive, 
											                 accumulator, 
											                 reducer, 
											                 distance+1, 
											                 visitedNodes, 
											                 parent());
					}
					break;
			}
		}
		
		return accumulator;
	}

	private <R extends Object> R reduceToForTasksToVisit(Class<R> resultClass, 
			                                             VisitDirection[] howToVisitNodes, 
										                 boolean transitive,
										                 R accumulator,
			                                             TaskReducer<R> reducer,
			                                             int distance,
			                                             Set<String> visitedNodes,
			                                             StoredTaskIds tasksToVisit) {
		T.call(this);

		return tasksToVisit.reduceTo(resultClass, accumulator, (index, taskId, accumulatorArg) -> {
			Task taskToVisit = graph.findTaskByPath(new Path(taskId));

			reduceToForTaskToVisit(resultClass, 
					 		       howToVisitNodes, 
					 		       transitive, 
					 		       accumulator, 
					 		       reducer, 
					 		       distance, 
					 		       visitedNodes, 
					 		       taskToVisit);

			return accumulatorArg;
		});
	}

	private <R extends Object> R reduceToForTaskToVisit(Class<R> resultClass, 
			                                            VisitDirection[] howToVisitNodes, 
			                                            boolean transitive, 
			                                            R accumulator, 
			                                            TaskReducer<R> reducer, 
			                                            int distance, 
			                                            Set<String> visitedNodes, 
			                                            Task taskToVisit) {
		T.call(this);
		
		try {

			accumulator = reducer.reduce(distance, taskToVisit, accumulator);

			if(transitive && !visitedNodes.contains(taskToVisit.id())) {
				visitedNodes.add(taskToVisit.id());

				accumulator = taskToVisit.reduceToImpl(resultClass, howToVisitNodes, transitive, accumulator, reducer, distance, visitedNodes);
			}

		} catch(Break b) { }
		
		return accumulator;
	}
	
	

	public FindResults findAll(VisitDirection[] howToVisitNodes, 
							   boolean transitive,
							   TaskMatcher matcher) {
		T.call(this);
		
		return reduceTo(FindResults.class, 
					    howToVisitNodes, 
				        transitive, 
				        new FindResults(),

		 (distance, task, findAllResults) -> {

			 if(matcher.match(task)) {
				 findAllResults.addOrUpdateFindResult(distance, task);
			 }
			 
			 return findAllResults;
		});
	}

	public boolean hasAtomicTaskOfType(Class<? extends AtomicTask> taskClass) {
		T.call(this);
		
		
		Boolean hasTaskOfType = false;
		
		hasTaskOfType = hasAtomicTaskOfType(taskClass, entryTasks);
		
		if(!hasTaskOfType) {
			hasTaskOfType = hasAtomicTaskOfType(taskClass, exitTasks);
		}

		return hasTaskOfType;
	}
	
	private boolean hasAtomicTaskOfType(Class<? extends AtomicTask> taskClass, StoredAtomicTasks atomicTasks) {
		T.call(this);

		return atomicTasks.reduceTo(Boolean.class, false, (index, task, hasTask) -> {
			if(hasTask) {

				throw new Break();

			}else if(task.getClass().equals(taskClass)) {
				
				hasTask = true;
			}

			return hasTask;
		});
	}

	public boolean isDone(StudentCompletionsByTaskId completions) {
		T.call(this);

		return status(completions).isDone();
	}

	
	public TaskStatus status(StudentCompletionsByTaskId completions) {
		T.call(this);
		
		CompletionByAtomicTaskId atomicTaskCompletions = null;
		if(completions != null) {
			atomicTaskCompletions = completions.valueOf(this.id());
		}

		TaskStatus status = null;
		
		if(hasParent() && !parent().areEntryTasksDone(atomicTaskCompletions)) {
			status = new BlockedWaitingForParent(parent().getPath());
		}
		
		if(status == null) {
			List<Task> previousTasksNotDone = tasksNotDone(completions, getPreviousTasks());
			if(!previousTasksNotDone.isEmpty()) {
				status = new BlockedWaitingForPreviousTasks(previousTasksNotDone);
			}
		}

		if(status == null && !areEntryTasksDone(atomicTaskCompletions)) {
			status = new StatusTodo();
		}

		if(status == null) {
			List<Task> subTasksNotDone = tasksNotDone(completions, getSubTasks());
			if(!subTasksNotDone.isEmpty()) {
				status = new BlockedWaitingForSubTasks(subTasksNotDone);
			}
		}

		if(status == null && !areExitTasksDone(atomicTaskCompletions)) {
			status = new StatusTodo();
		}
		
		if(status == null) {
			status = new StatusDone();
		}
		
		return status;
	}
	
	@SuppressWarnings("unchecked")
	private List<Task> tasksNotDone(StudentCompletionsByTaskId completions, StoredTaskIds tasks){
		T.call(this);
		
		return tasks.reduceTo(List.class, new ArrayList<Task>(), (index, taskId, accumulator) -> {
			Task task = graph.findTaskByPath(new Path(taskId));
			
			if(!task.isDone(completions)) {
				accumulator.add(task);
			}

			return accumulator;
		});
	}

	private boolean areEntryTasksDone(CompletionByAtomicTaskId completions) {
		T.call(this);

		return areAtomicTasksDone(completions, getEntryTasks());
	}

	private boolean areExitTasksDone(CompletionByAtomicTaskId completions) {
		T.call(this);

		return areAtomicTasksDone(completions, getExitTasks());
	}

	private boolean areAtomicTasksDone(CompletionByAtomicTaskId completions, StoredAtomicTasks atomicTasks) {
		T.call(this);

		return atomicTasks.reduceTo(Boolean.class, true, (index, entryTask, parentEntryDone) -> {
			if(!parentEntryDone) {
				throw new Break();
			}
			
			AtomicTaskCompletion completion = null;
			if(completions != null) {
				completion = completions.valueOf(entryTask.getId());
			}

			if(completion == null
					|| !completion.isCompleted()) {
				
				parentEntryDone = false;
			}
			
			return parentEntryDone;
		});
	}
}
