package ca.aquiletour.core.models.courses.status;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.courses.base.Task;
import ca.ntro.core.system.trace.T;

public class BlockedWaitingForTasks extends StatusBlocked {
	
	private List<Task> tasksTodo = new ArrayList<>();

	public BlockedWaitingForTasks() {
		super();
		T.call(this);
	}

	public BlockedWaitingForTasks(List<Task> tasksTodo) {
		super();
		T.call(this);
		
		this.tasksTodo = tasksTodo;
	}

	public List<Task> getTasksTodo() {
		return tasksTodo;
	}

	public void setTasksTodo(List<Task> tasksTodo) {
		this.tasksTodo = tasksTodo;
	}
	
	

}
