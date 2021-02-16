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

	void execute();
	void execute(GraphTraceWriter writer);

	void notifyEntryTaskFinished();
	void notifyExitTaskFinished();
	void notifySomePreviousTaskFinished(NtroTask finishedTask);
	void notifySomeSubTaskFinished(NtroTask finishedTask);
	
	TaskGraph asGraph();
	Node asNode();

}
	