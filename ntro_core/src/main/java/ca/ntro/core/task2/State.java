package ca.ntro.core.task2;

public enum State {

	INACTIVE,
	PAUSED,                       // XXX: we pause to modify 
	                              //      the graph
	                              //      hum? but then
	                              //      modifying the graph
	                              //      is not sync
	WAITING_FOR_PREVIOUS_TASKS,
	RUNNING_ENTRY_TASK,
	WAITING_FOR_SUB_TASKS,
	RUNNING_EXIT_TASK,
	DONE;

}
