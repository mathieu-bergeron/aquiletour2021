package ca.aquiletour.core.models.courses.base;

import static ca.aquiletour.core.models.courses.base.lambdas.VisitDirection.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;
import ca.aquiletour.core.models.courses.atomic_tasks.default_task.DefaultAtomicTask;
import ca.aquiletour.core.models.courses.base.lambdas.BreakableAccumulator;
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
import ca.aquiletour.core.models.dates.AquiletourDate;
import ca.aquiletour.core.models.dates.CourseDate;
import ca.aquiletour.core.models.dates.SemesterDate;
import ca.aquiletour.core.models.dates.StoredAquiletourDate;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.models.lambdas.Break;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

public class Task implements NtroModelValue, TaskNode {
	
	private TaskGraph graph;

	private TaskPath path = new TaskPath();

	private StoredString title = new StoredString();
	private StoredString description = new StoredString();
	private StoredAquiletourDate endTime = new StoredAquiletourDate();

	private StoredAtomicTasks entryTasks = new StoredAtomicTasks();
	private StoredAtomicTasks exitTasks = new StoredAtomicTasks();
	
	private StoredTaskIds previousTasks = new StoredTaskIds();
	private StoredTaskIds subTasks = new StoredTaskIds();
	private StoredTaskIds nextTasks = new StoredTaskIds();
	
	public Task() {
		T.call(this);

		exitTasks.addItem(new DefaultAtomicTask());
	}

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
			
			refreshAtomicTasks();
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

	public void forEachSubTaskInOrder(TaskForEach lambda) {
		T.call(this);
		
		FindResults findResults = reduceToForStoredTasks(getSubTasks(), FindResults.class, new FindResults(), (index, subTask, accumulator) -> {

			FindResult distanceToSubTask = distanceToTask(new VisitDirection[] {SUB, NEXT}, subTask);
			
			accumulator.addOrUpdateFindResult(distanceToSubTask);
			
			return accumulator;
		});

		findResults.sort((findResult1, findResult2) -> {
			return Integer.compare(findResult1.getMaxDistance(), findResult2.getMaxDistance());
		});
		
		findResults.forEachTask(lambda);
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

		FindResults findResults = reduceToForStoredTasks(getPreviousTasks(), FindResults.class, new FindResults(), (index, previousTask, accumulator) -> {

			FindResult distanceToPreviousTask = distanceToTask(new VisitDirection[] {PREVIOUS}, previousTask);
			
			accumulator.addOrUpdateFindResult(distanceToPreviousTask);
			
			return accumulator;
		});
		
		findResults.sort((findResult1, findResult2) -> {
			return Integer.compare(findResult1.getMaxDistance(), findResult2.getMaxDistance());
		});
		
		findResults.forEachTask(lambda);
	}

	public void forEachNextTaskInOrder(TaskForEach lambda) {
		T.call(this);

		FindResults findResults = reduceToForStoredTasks(getNextTasks(), FindResults.class, new FindResults(), (index, nextTask, accumulator) -> {
			
			FindResult distanceToNextTask = distanceToTask(new VisitDirection[] {NEXT}, nextTask);
			
			accumulator.addOrUpdateFindResult(distanceToNextTask);
			
			return accumulator;
		});
		
		findResults.sort((findResult1, findResult2) -> {
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
		
		refreshAtomicTasks(description, atomicTaskListener);
	}

	private void refreshAtomicTasks(String description, OnAtomicTaskAdded atomicTaskListener) {
		T.call(this);

		getEntryTasks().clearItems();
		getExitTasks().clearItems();

		AtomicTask.addAtomicTasksFromDescription(this, description, atomicTaskListener);
	}

	private void refreshAtomicTasks() {
		T.call(this);
		
		refreshAtomicTasks(getDescription().getValue(), new OnAtomicTaskAdded() {
			@Override
			public void onAtomicTaskAdded(Task task, AtomicTask atomicTask) {
			}
		});
	}
	
	public void deleteAtomicTask(String atomicTaskId) {
		T.call(this);

	}
	
	public void updateTitle(String title) {
		T.call(this);
		
		getTitle().set(title);
	}
	
	public void updateEndTime(AquiletourDate endTime) {
		T.call(this);
		
		getEndTime().set(endTime);
	}

	public StoredAquiletourDate getEndTime() {
		return endTime;
	}

	public void setEndTime(StoredAquiletourDate endTime) {
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
		
		AquiletourDate endTimeDate = endTime.getValue();
		
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

		AquiletourDate endTimeDate = endTime.getValue();
		
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

	AtomicTask atomicTaskById(String id) {
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

	public void forEachTaskBackwardsTransitive(TaskForEach lambda) {
		T.call(this);

		if(!isRootTask()) {
			parent().forEachTaskBackwardsTransitive(lambda);
		}
		
		forEachPreviousTask(pt -> {
			pt.forEachTaskBackwardsTransitive(lambda);
		});
	}

	// FIXME: O(n^n) implementation
	//        that does memorize the cycle path
	public GraphPath findFirstCycle() {
		T.call(this);

		return reduceTo(GraphPath.class, new VisitDirection[] {SUB,NEXT}, true, null, (d, task, outerCycle)-> {
			if(outerCycle != null) {
				throw new Break();
			}

			return task.reduceTo(GraphPath.class, new VisitDirection[] {SUB,NEXT}, true, outerCycle, (distance, reachableTask, cycle) -> {
				if(cycle != null) {
					throw new Break();
				}
				
				if(distance > 0 && reachableTask == task) {
					cycle =  new GraphPath();
					cycle.addName(task.id());
				}

				return cycle;
			});
			
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

		BreakableAccumulator<R> breakableAccumulator = reduceToImpl(resultClass, howToVisitNodes, transitive, accumulator, reducer, 0, visistedNodes);
		accumulator = breakableAccumulator.getAccumulator();
		
		return accumulator;
	}

	private <R extends Object> BreakableAccumulator<R> reduceToImpl(Class<R> resultClass, 
			                                                        VisitDirection[] howToVisitNodes, 
										                            boolean transitive,
										                            R accumulator,
			                                                        TaskReducer<R> reducer,
			                                                        int distance,
			                                                        Set<String> visitedNodes) {
		T.call(this);
		
		BreakableAccumulator<R> breakableAccumulator = new BreakableAccumulator<R>(accumulator);
		
		for(VisitDirection direction : howToVisitNodes) {
			if(direction == PREVIOUS) {

				breakableAccumulator = reduceToForTasksToVisit(resultClass, 
													           howToVisitNodes, 
													           transitive, 
													           accumulator, 
													           reducer, 
													           distance+1, 
													           visitedNodes,
													           getPreviousTasks());

				accumulator = breakableAccumulator.getAccumulator();

				if(breakableAccumulator.shouldBreak()) {
					break;
				}

			}else if(direction == SUB) {

				breakableAccumulator = reduceToForTasksToVisit(resultClass, 
													           howToVisitNodes, 
													           transitive, 
													           accumulator, 
													           reducer, 
													           distance+1, 
													           visitedNodes, 
													           getSubTasks());

				accumulator = breakableAccumulator.getAccumulator();

				if(breakableAccumulator.shouldBreak()) {
					break;
				}

			}else if(direction == NEXT) {

				breakableAccumulator = reduceToForTasksToVisit(resultClass, 
													           howToVisitNodes, 
													           transitive, 
													           accumulator, 
													           reducer, 
													           distance+1, 
													           visitedNodes, 
													           getNextTasks());

				accumulator = breakableAccumulator.getAccumulator();

				if(breakableAccumulator.shouldBreak()) {
					break;
				}
				
			}else if(direction == PARENT) {

				if(!isRootTask()) {
					breakableAccumulator = reduceToForTaskToVisit(resultClass, 
																  howToVisitNodes, 
																  transitive, 
																  accumulator, 
																  reducer, 
																  distance+1, 
																  visitedNodes, 
																  parent());
					
					accumulator = breakableAccumulator.getAccumulator();
					if(breakableAccumulator.shouldBreak()) {
						break;
					}
				}
			}
		}
		
		return breakableAccumulator;
	}
	
	private <ACC extends Object> ACC reduceToForStoredTasks(StoredTaskIds storedTasksIds, 
			                                               Class<ACC> accumulatorClass, 
			                                               ACC accumulator, 
			                                               TaskReducer<ACC> reducer){
		
		return storedTasksIds.reduceTo(accumulatorClass, accumulator, (index, taskId, innerAccumulator) -> {
			Task task = graph.findTaskByPath(Path.fromRawPath(taskId));
			
			return reducer.reduce(index, task, innerAccumulator);
		});
	}
	
	private <R extends Object> BreakableAccumulator<R> reduceToForTasksToVisit(Class<R> resultClass, 
			                                                                   VisitDirection[] howToVisitNodes, 
										                                       boolean transitive,
										                                       R accumulator,
			                                                                   TaskReducer<R> reducer,
			                                                                   int distance,
			                                                                   Set<String> visitedNodes,
			                                                                   StoredTaskIds tasksToVisit) {
		T.call(this);
		
		BreakableAccumulator<R> breakableAccumulator = new BreakableAccumulator<R>(accumulator);
		
		accumulator = reduceToForStoredTasks(tasksToVisit, resultClass, accumulator, (index, taskToVisit, innerAccumulator) -> {
			if(breakableAccumulator.shouldBreak()) {
				throw new Break();
			}
			
			BreakableAccumulator<R> innerBreakable = reduceToForTaskToVisit(resultClass, 
																			howToVisitNodes, 
																			transitive, 
																			innerAccumulator,
																			reducer, 
																			distance, 
																			visitedNodes, 
																			taskToVisit);
			
			innerAccumulator = innerBreakable.getAccumulator();
			
			if(innerBreakable.shouldBreak()) {
				breakableAccumulator.setShouldBreak(true);
			}

			return innerAccumulator;
		});
		
		breakableAccumulator.setAccumulator(accumulator);
		
		return breakableAccumulator;
	}

	private <R extends Object> BreakableAccumulator<R> reduceToForTaskToVisit(Class<R> resultClass, 
			                                                                  VisitDirection[] howToVisitNodes, 
			                                                                  boolean transitive, 
			                                                                  R accumulator, 
			                                                                  TaskReducer<R> reducer, 
			                                                                  int distance, 
			                                                                  Set<String> visitedNodes, 
			                                                                  Task taskToVisit) {
		T.call(this);
		
		BreakableAccumulator<R> breakableAccumulator = new BreakableAccumulator<R>(accumulator);

		try { 

			accumulator = reducer.reduce(distance, taskToVisit, accumulator);
			breakableAccumulator.setAccumulator(accumulator);

			if(transitive && !visitedNodes.contains(taskToVisit.id())) {
				visitedNodes.add(taskToVisit.id());

				breakableAccumulator = taskToVisit.reduceToImpl(resultClass, howToVisitNodes, transitive, accumulator, reducer, distance, visitedNodes);
			}

		}catch(Break b) {
			
			breakableAccumulator.setShouldBreak(true);
		}

		return breakableAccumulator;
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

		return reduceToForStoredTasks(tasks, List.class, new ArrayList<Task>(), (index, task, accumulator) -> {
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

		return atomicTasks.reduceTo(Boolean.class, true, (index, atomicTask, tasksDone) -> {
			if(!tasksDone) {
				throw new Break();
			}

			AtomicTaskCompletion completion = null;
			if(completions != null) {
				completion = completions.valueOf(atomicTask.getId());
			}

			if(completion == null
					|| !completion.isCompleted()) {
				
				tasksDone = false;
			}

			return tasksDone;
		});
	}

}
