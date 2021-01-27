package ca.ntro.core.tasks;

public interface NtroTask {

	void setTaskId(String taskId);
	void reset();
	void execute();
	NtroTask addPreviousTask(NtroTaskImpl task);
	NtroTask addNextTask(NtroTaskImpl task);

}
