package ca.ntro.core.task2;

public enum TaskState {

	INIT,
	WAITING_FOR_PARENT,
	WAITING_FOR_PREVIOUS_TASKS,
	RUNNING_ENTRY_TASK,
	WAITING_FOR_SUB_TASKS,
	RUNNING_EXIT_TASK,
	DONE,
	DELETED;                
}
