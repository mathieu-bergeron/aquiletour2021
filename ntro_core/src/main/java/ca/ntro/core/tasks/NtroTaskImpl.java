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

import java.util.HashSet;
import java.util.Set;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.system.trace.__T;

public abstract class NtroTaskImpl implements NtroTask {

	private NtroTask parentTask;

	private Set<NtroTask> previousTasks = new HashSet<>();
	private int finishedPreviousTasks = 0;

	private Set<NtroTask> subTasks = new HashSet<>();
	private int finishedSubTasks = 0;

	private Set<NtroTask> nextTasks = new HashSet<>();

	private State state = State.INITIALIZING;
	
	private String taskId;
	
	protected abstract void initializeTask();
	protected abstract void runTaskAsync();
	protected abstract void onFailure(Exception e);
	
	public NtroTaskImpl() {
		initializeTask();
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

		state = State.INITIALIZING;

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

		if(state == State.INITIALIZING) {
			startExecution();
		}
	}
	
	@Override
	public State getState() {
		return state;
	}
	
	private void startExecution() {
		//__T.call(this, "startExecution");

		if(parentTask != null 
				&& parentTask.getState() == State.INITIALIZING) {

			parentTask.execute();

		}else {

			state = State.EXECUTE_PREVIOUS_TASKS;
			resumeExecution();
		}
	}
	
	@Override
	public void setParentTask(NtroTask parentTask) {
		if(state == State.INITIALIZING) {
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
					state = State.EXECUTE_SUB_TASKS;
					resumeExecution();
				}
				break;

			case EXECUTE_SUB_TASKS:
				if(ifShouldExecuteSubTasks()) {
					executeSubTasks();
				}else {
					state = State.EXECUTE_CURRENT_TASK;
					resumeExecution();
				}
				break;

			case EXECUTE_CURRENT_TASK:
				runTaskAsync();
				break;

			case EXECUTE_NEXT_TASKS:
				executeNextTasks();
				state = State.DONE;
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

		if(state == State.EXECUTE_PREVIOUS_TASKS) {
			resumeExecution();
		}
	}
	
	protected void notifyTaskFinished() {
		//__T.call(this, "notifyTaskFinished");
		
		if(parentTask != null) {
			parentTask.notifySomeSubTaskFinished();
		}
		
		state = State.EXECUTE_NEXT_TASKS;
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
			state = State.EXECUTE_CURRENT_TASK;
			resumeExecution();
		}
	}

	private void executeNextTasks() {
		//__T.call(this, "executeNextTasks");

		for(NtroTask nextTask : nextTasks) {
			nextTask.notifySomePreviousTaskFinished();
			if(nextTask.getState() == State.INITIALIZING) {
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
		return getFinishedTask(taskClass, taskClass.getSimpleName());
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

		if(state == State.INITIALIZING) {
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
		}else {
			throw new IllegalStateException("Task.addPreviousTask called on state " + state);
		}
		
		return this;
	}

	@Override
	public NtroTask addNextTask(NtroTask task) {
		if(state == State.INITIALIZING) {
			if(!nextTasks.contains(task)) {
				nextTasks.add(task);
				task.addPreviousTask(this);

				if(parentTask != null) {
					task.setParentTask(parentTask);
					parentTask.addSubTask(task);
				}
			}else {
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

		if(state == State.INITIALIZING) {
			if(!hasSubTask(taskId)) {
				task.setParentTask(this);
				subTasks.add(task);
			}else {
				System.err.println("[WARNING] a task already exists with id " + taskId);
			}
		}else {
			throw new IllegalStateException("Task.addSubTask called on state " + state + " for task " + getTaskId());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		
		write(out, 0);
		
		return out.toString();
	}

	@Override
	public void write(StringBuilder out, int indentLevel) {
		T.call(this);
		
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
	private void writeTaskSet(StringBuilder out, int indentLevel, String taskSetName, Set<NtroTask> taskSet) {

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
		T.call(this);
		for(int i = 0; i < indentLevel*4; i++) {
			out.append(" ");
		}
	}
	
	
}
