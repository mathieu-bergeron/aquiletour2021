package ca.ntro.core.task2;

public enum State {

	BEFORE_EXECUTION,
	WAITING_FOR_PREVIOUS_TASKS,
	RUNNING_ENTRY_TASK,
	WAITING_FOR_SUB_TASKS,
	RUNNING_EXIT_TASK,
	AFTER_EXECUTION,
	REMOVED_FROM_GRAPH;                
}
