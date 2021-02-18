// Copyright (C) (2020) (Mathieu Bergeron) (mathieu.bergeron@cmontmorency.qc.ca)
//
// This file is part of tutoriels4f5
//
// tutoriels4f5 is free software: you can redistribute it and/or modify
// it under the terms of the GNU Affero General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// tutoriels4f5 is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Affero General Public License for more details.
//
// You should have received a copy of the GNU Affero General Public License
// along with aquiletour.  If not, see <https://www.gnu.org/licenses/>

package ca.ntro.core.tasks;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.Ntro;
import ca.ntro.core.system.trace.__T;
import jsweet.util.StringTypes.pre;

public abstract class NtroTaskAsync implements NtroTask {
	
	private boolean  REPLACE_TASKS_WHILE_EXECUTING = true;

	private NtroTask parentTask;

	// XXX: must be ArrayList since we need to
	//      replace tasks while interating over lists
	//      (w/o running into a ConcurrentModification exception)
	private List<NtroTask> previousTasks = new ArrayList<>();
	private int finishedPreviousTasks = 0;

	private List<NtroTask> subTasks = new ArrayList<>();
	private int finishedSubTasks = 0;

	private List<NtroTask> nextTasks = new ArrayList<>();

	private TaskState state = TaskState.INITIALIZING;

	private String taskId;

	protected abstract void initializeTask();
	protected abstract void runTaskAsync();
	protected abstract void onFailure(Exception e);

	public NtroTaskAsync() {
		initializeTask();
	}
	
	@Override
	public void destroy() {
		if(parentTask != null) {
			parentTask.destroy();
			parentTask = null;
		}
		
		List<NtroTask> tasksToDetroy = new ArrayList<>();
		tasksToDetroy.addAll(previousTasks);
		tasksToDetroy.addAll(subTasks);
		tasksToDetroy.addAll(nextTasks);

		previousTasks = new ArrayList<>();
		subTasks = new ArrayList<>();
		nextTasks = new ArrayList<>();

		for(NtroTask taskToDestroy : tasksToDetroy) {
			taskToDestroy.destroy();
		}
	}

	@Override
	public void setTaskId(String taskId) {
		// __T.call(this, "setTaskId");

		this.taskId = taskId;
	}

	@Override
	public String getTaskId() {
		if(taskId == null) {
			taskId = defaultId(this);
		}

		return taskId;
	}

	@Override
	public void reset() {
		// FIXME: this requires more thought

		state = TaskState.INITIALIZING;

		for(NtroTask subTask : subTasks) {
			subTask.reset();
		}
		finishedSubTasks = 0;

		for(NtroTask nextTask : nextTasks) {
			nextTask.reset();
		}
	}

	@Override
	public void execute() {
		//__T.call(this, "execute");

		if(state == TaskState.INITIALIZING) {
			startExecution();
		}
	}

	@Override
	public TaskState getState() {
		return state;
	}

	private void startExecution() {
		//__T.call(this, "startExecution");

		if(parentTask != null
				&& parentTask.getState() == TaskState.INITIALIZING) {

			parentTask.execute();

		}else {

			state = TaskState.EXECUTE_PREVIOUS_TASKS;
			resumeExecution();
		}
	}

	@Override
	public void setParentTask(NtroTask parentTask) {
		if(state == TaskState.INITIALIZING) {
			this.parentTask = parentTask;
		}else {
			throw new IllegalStateException("Task.setParentTask called on state " + state);
		}
	}

	private void resumeExecution() {
		//__T.call(this, "resumeExecution");
		//System.out.println(state);

		switch(state) {
			case EXECUTE_PREVIOUS_TASKS:
				if(ifShouldExecutePreviousTasks()) {
					executePreviousTasks();
				}else {
					state = TaskState.EXECUTE_SUB_TASKS;
					resumeExecution();
				}
				break;

			case EXECUTE_SUB_TASKS:
				if(ifShouldExecuteSubTasks()) {
					executeSubTasks();
				}else {
					state = TaskState.EXECUTE_CURRENT_TASK;
					resumeExecution();
				}
				break;

			case EXECUTE_CURRENT_TASK:
				runTaskAsync();
				break;

			case EXECUTE_NEXT_TASKS:
				executeNextTasks();
				state = TaskState.DONE;
				break;

			case DONE:
			default:
				break;
		}
	}

	private void executePreviousTasks() {
		for(NtroTask previousTask : previousTasks) {
			previousTask.execute();
		}
	}

	private boolean ifShouldExecutePreviousTasks() {
		return finishedPreviousTasks < previousTasks.size();
	}

	@Override
	public void notifySomePreviousTaskFinished() {
		//__T.call(this, "notifySomePreviousTaskFinished");

		finishedPreviousTasks++;

		if(state == TaskState.EXECUTE_PREVIOUS_TASKS) {
			resumeExecution();
		}
	}

	protected void notifyTaskFinished() {
		//__T.call(this, "notifyTaskFinished");

		if(parentTask != null) {
			parentTask.notifySomeSubTaskFinished();
		}

		state = TaskState.EXECUTE_NEXT_TASKS;
		resumeExecution();
	}

	protected void notifyTaskFailed(Exception e) {
		//__T.call(this, "notifyTaskFailed");

		// TODO: propagate failure to
		//       parentTask
		//       nextTasks

		throw new RuntimeException("TODO");
	}

	@Override
	public void notifySomeSubTaskFinished() {
		//__T.call(this, "notifySomeSubTaskFinished");

		finishedSubTasks++;

		if(finishedSubTasks == subTasks.size()) {
			state = TaskState.EXECUTE_CURRENT_TASK;
			resumeExecution();
		}
	}

	private void executeNextTasks() {
		//__T.call(this, "executeNextTasks");

		for(NtroTask nextTask : nextTasks) {
			nextTask.notifySomePreviousTaskFinished();
			if(nextTask.getState() == TaskState.INITIALIZING) {
				nextTask.execute();
			}
		}
	}

	protected <T extends NtroTask> T getPreviousTask(Class<T> taskClass) {
		return getPreviousTask(taskClass, defaultIdFromClass(taskClass));
	}

	@SuppressWarnings("unchecked")
	protected <T extends NtroTask> T getPreviousTask(Class<T> taskClass, String id) {
		return (T) getPreviousTask(id);
	}

	private NtroTask getPreviousTask(String id) {
		NtroTask task = null;

		for(NtroTask previousTask : previousTasks) {
			if(previousTask.hasId(id)) {
				task = previousTask;
				break;
			}
		}

		return task;
	}

	@Override
	public boolean hasId(String id) {
		return id.equals(taskId);
	}

	@Override
	public <NT extends NtroTask> NT getSubTask(Class<NT> taskClass) {
		return getSubTask(taskClass, defaultIdFromClass(taskClass));
	}

	@SuppressWarnings("unchecked")
	public <NT extends NtroTask> NT getSubTask(Class<NT> taskClass, String id) {
		return (NT) getSubTask(id);
	}

	private NtroTask getSubTask(String id) {
		NtroTask task = null;

		for(NtroTask subTask : subTasks) {
			if(subTask.hasId(id)) {
				task = subTask;
				break;
			}
		}

		return task;

	}

	protected <T extends NtroTask> T getFinishedTask(Class<T> taskClass) {
		return getFinishedTask(taskClass, Ntro.introspector().getSimpleNameForClass(taskClass));
	}

	@SuppressWarnings("unchecked")
	protected <T extends NtroTask> T getFinishedTask(Class<T> taskClass, String id) {
		T finishedTask = getSubTask(taskClass, id);
		if(finishedTask == null) {
			finishedTask = getPreviousTask(taskClass, id);
		}
		return finishedTask;
	}

	private String defaultId(NtroTask task) {
		return defaultIdFromClass(task.getClass());
	}

	private String defaultIdFromClass(Class<? extends NtroTask> taskClass) {
		// Introspector is not registered here
		return taskClass.getSimpleName();
	}

	private boolean hasPreviousTask(String taskId) {
		return getPreviousTask(taskId) != null;
	}

	private boolean hasSubTask(String taskId) {
		return getSubTask(taskId) != null;
	}

	@Override
	public NtroTask addPreviousTask(NtroTask task) {
		String taskId = task.getTaskId();

		if(state == TaskState.INITIALIZING) {
			if(!hasPreviousTask(taskId)) {
				previousTasks.add(task);
				task.addNextTask(this);

				if(parentTask != null) {
					task.setParentTask(parentTask);
					parentTask.addSubTask(task);
				}
			}else {
				System.err.println("[WARNING] a previousTask already exists with id " + taskId);
			}
		}else if(REPLACE_TASKS_WHILE_EXECUTING){
			NtroTask existingPreviousTask = getPreviousTask(taskId);
			if(existingPreviousTask != null) {
				int existingPreviousTaskIndex = subTasks.indexOf(existingPreviousTask);
				((NtroTaskAsync)task).mimicTask((NtroTaskAsync) existingPreviousTask);

				// XXX: must be done by index otherwise
				//      we have a ConcurrentModification exception
				previousTasks.set(existingPreviousTaskIndex, task);
				
				((NtroTaskAsync)task).state = TaskState.EXECUTE_PREVIOUS_TASKS;
				((NtroTaskAsync)task).resumeExecution();
			}

		}else {
			throw new IllegalStateException("Task.addPreviousTask called on state " + state);
		}

		return this;
	}

	@Override
	public NtroTask addNextTask(NtroTask task) {
		if(state == TaskState.INITIALIZING) {
			if(!nextTasks.contains(task)) {
				nextTasks.add(task);
				task.addPreviousTask(this);

				if(parentTask != null) {
					task.setParentTask(parentTask);
					parentTask.addSubTask(task);
				}
			}else {
				// Introspector might not be registered here
				System.err.println("[WARNING] a nextTask already exists with class " + task.getClass().getSimpleName());
			}
		}else {
			throw new IllegalStateException("Task.addPreviousTask called on state " + state);
		}

		return this;
	}

	private boolean ifShouldExecuteSubTasks() {
		return finishedSubTasks < subTasks.size();
	}

	private void executeSubTasks() {
		for(NtroTask subTask : subTasks) {
			subTask.execute();
		}
	}

	@Override
	public void addSubTask(NtroTask task) {
		String taskId = task.getTaskId();

		if(state == TaskState.INITIALIZING) {
			if(!hasSubTask(taskId)) {
				task.setParentTask(this);
				subTasks.add(task);
			}else {
				System.err.println("[WARNING] a task already exists with id " + taskId);
			}
		}else if(REPLACE_TASKS_WHILE_EXECUTING){
			NtroTask existingSubTask = getSubTask(taskId);
			if(existingSubTask != null) {
				int existingSubTaskIndex = subTasks.indexOf(existingSubTask);
				((NtroTaskAsync)task).mimicTask((NtroTaskAsync) existingSubTask);

				// XXX: must be done by index otherwise
				//      we have a ConcurrentModification exception
				subTasks.set(existingSubTaskIndex, task);
				
				((NtroTaskAsync)task).state = TaskState.EXECUTE_PREVIOUS_TASKS;
				((NtroTaskAsync)task).resumeExecution();
			}
		}else {
			throw new IllegalStateException("Task.addSubTask called on state " + state);
		}
	}
	
	private void mimicTask(NtroTaskAsync otherTask) {
		
		setTaskId(otherTask.getTaskId());

		previousTasks.addAll(otherTask.previousTasks);
		subTasks.addAll(otherTask.subTasks);
		nextTasks.addAll(otherTask.nextTasks);
		setParentTask(otherTask.parentTask);

		// FIXME:
		// previousTasks.forEach(pt -> pt.replaceNext(otherTask, this))
		// nextTasks.forEach(nt -> nt.replacePrevious(otherTask, this))
		
		for(NtroTask nextTask : nextTasks) {
			((NtroTaskAsync)nextTask).replacePreviousTask(otherTask, this);
		}
	}
	
	private void replacePreviousTask(NtroTask oldTask, NtroTask newTask) {
		int oldTaskIndex = previousTasks.indexOf(oldTask);
		previousTasks.set(oldTaskIndex, newTask);
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();

		write(out, 0);

		return out.toString();
	}

	@Override
	public void write(StringBuilder out, int indentLevel) {

		indent(out, indentLevel);
		out.append(getTaskId());
		out.append(" {\n");

		if(indentLevel == 0) {
			writeTaskSet(out, indentLevel+1, "previousTasks", previousTasks);
		}
		
		writeTaskSet(out, indentLevel+1, "subTasks", subTasks);
		writeTaskSet(out, indentLevel+1, "nextTasks", nextTasks);

		indent(out, indentLevel);
		out.append("}\n");


	}

	private void writeTaskSet(StringBuilder out, int indentLevel, String taskSetName, List<NtroTask> taskSet) {
		indent(out, indentLevel);
		out.append(taskSetName);
		out.append(" {\n");
		for(NtroTask task: taskSet) {
			task.write(out, indentLevel+1);
		}
		indent(out, indentLevel);
		out.append("}\n");
	}

	private void indent(StringBuilder out, int indentLevel) {
		for(int i = 0; i < indentLevel*4; i++) {
			out.append(" ");
		}
	}


}
