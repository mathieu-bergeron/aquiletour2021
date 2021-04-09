package ca.ntro.core.tasks;

public interface NtroTask {
	
	String getTaskId();

	void setTaskId(String id);
	void setParentTask(NtroTask parentTask);

	NtroTask addSubTask(NtroTask task);
	NtroTask addSubTask(NtroTask task, String taskId);

	NtroTask addNextTask(NtroTask task);
	NtroTask addNextTask(NtroTask task, String taskId);

	NtroTask addPreviousTask(NtroTask task);
	NtroTask addPreviousTask(NtroTask task, String taskId);
	
	<N extends NtroTask> N getPreviousTask(Class<N> taskClass, String taskId);
	<N extends NtroTask> N getSubTask(Class<N> taskClass, String taskId);
	
	void replaceWith(NtroTask task);

	GraphTraceConnector execute();

	void notifyEntryTaskFinished();
	void notifyExitTaskFinished();

	void resetTask(TaskState state);
	
	TaskGraph asGraph();
	Node asNode();

}
	