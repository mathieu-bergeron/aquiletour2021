package ca.ntro.core.task2;

public interface NtroTask extends Identifiable {

	void setId(String id);
	void setParentTask(NtroTask parentTask);

	void addSubTask(NtroTask task);
	void addSubTask(NtroTask task, String taskId);

	void addNextTask(NtroTask task);
	void addNextTask(NtroTask task, String taskId);

	void addPreviousTask(NtroTask task);
	void addPreviousTask(NtroTask task, String taskId);


	GraphTraceConnector execute();

	void notifyEntryTaskFinished();
	void notifyExitTaskFinished();

	void resetTask();
	
	TaskGraph asGraph();
	Node asNode();

}
	