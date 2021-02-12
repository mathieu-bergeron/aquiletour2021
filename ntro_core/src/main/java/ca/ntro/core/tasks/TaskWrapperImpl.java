package ca.ntro.core.tasks;

import ca.ntro.core.system.trace.T;

public abstract class TaskWrapperImpl implements TaskWrapper {
	
	private NtroTask task;
	
	public TaskWrapperImpl(NtroTask task) {
		T.call(this);
		
		this.task = task;
	}

	@Override
	public NtroTask getTask() {
		T.call(this);

		return task;
	}

}
