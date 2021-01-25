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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ca.ntro.core.system.trace.__T;

public abstract class NtroTask {

	private NtroTask parentTask;

	private Map<String, NtroTask> previousTasks = new HashMap<>();
	private int finishedPreviousTasks = 0;

	private Map<String, NtroTask> subTasks = new HashMap<>();
	private int finishedSubTasks = 0;

	private Set<NtroTask> nextTasks = new HashSet<>();

	private State state = State.INITIALIZING;
	
	protected abstract void runTask();
	protected abstract void onFailure(Exception e);

	public void execute() {
		//__T.call(this, "execute");

		if(state == State.INITIALIZING) {
			startExecution();
		}
	}
	
	protected State getState() {
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
				runTask();
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
		for(NtroTask previousTask : previousTasks.values()) {
			previousTask.execute();
		}
	}

	private boolean ifShouldExecutePreviousTasks() {
		return finishedPreviousTasks < previousTasks.size();
	}

	void notifySomePreviousTaskFinished() {
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

	protected void notifySomeSubTaskFinished() {
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
		return getPreviousTask(taskClass, taskClass.getSimpleName());
	}

	@SuppressWarnings("unchecked")
	protected <T extends NtroTask> T getPreviousTask(Class<T> taskClass, String id) {
		return (T) previousTasks.get(id);
	}

	protected <T extends NtroTask> T getSubTask(Class<T> taskClass) {
		return getSubTask(taskClass, defaultIdFromClass(taskClass));
	}

	@SuppressWarnings("unchecked")
	protected <T extends NtroTask> T getSubTask(Class<T> taskClass, String id) {
		return (T) subTasks.get(id);
	}

	protected <T extends NtroTask> T getFinishedTask(Class<T> taskClass) {
		return getFinishedTask(taskClass, taskClass.getSimpleName());
	}

	@SuppressWarnings("unchecked")
	protected <T extends NtroTask> T getFinishedTask(Class<T> taskClass, String id) {
		T finishedTask = (T) subTasks.get(id);
		if(finishedTask == null) {
			finishedTask = (T) previousTasks.get(id);
		}
		return finishedTask;
	}
	
	private String defaultId(NtroTask task) {
		return defaultIdFromClass(task.getClass());
	}

	private String defaultIdFromClass(Class<? extends NtroTask> taskClass) {
		return taskClass.getSimpleName();
	}

	public NtroTask addPreviousTask(NtroTask task) {
		return addPreviousTask(task, defaultId(task));
	}

	public NtroTask addPreviousTask(NtroTask task, String taskId) {
		if(state == State.INITIALIZING) {
			if(!previousTasks.containsKey(taskId)) {
				previousTasks.put(taskId, task);
				task.addNextTask(this);
			}
		}else {
			throw new IllegalStateException("Task.addPreviousTask called on state " + state);
		}
		
		return this;
	}

	public NtroTask addNextTask(NtroTask task) {
		if(state == State.INITIALIZING) {
			if(!nextTasks.contains(task)) {
				nextTasks.add(task);
				task.addPreviousTask(this);
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
		for(NtroTask subTask : subTasks.values()) {
			subTask.execute();
		}
	}

	public void addSubTask(NtroTask task, String taskId) {
		if(state == State.INITIALIZING) {
			task.setParentTask(this);
			subTasks.put(taskId, task);
		}else {
			throw new IllegalStateException("Task.addSubTask called on state " + state);
		}
	}

	public void addSubTask(NtroTask task) {
		addSubTask(task, defaultId(task));
	}
	
}
