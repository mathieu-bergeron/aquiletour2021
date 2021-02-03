package ca.ntro.core.tasks;

public interface NtroTask {

	String getTaskId();
	void setTaskId(String taskId);
	void reset();
	void execute();
	NtroTask addPreviousTask(NtroTask task);
	NtroTask addNextTask(NtroTask task);
	void notifySomePreviousTaskFinished();
	State getState();
	boolean hasId(String id);
	void addSubTask(NtroTask task);
	void notifySomeSubTaskFinished();
	void setParentTask(NtroTask ntroTaskImpl);
	<NT extends NtroTask> NT getSubTask(Class<NT> taskClass);
	<NT extends NtroTask> NT getSubTask(Class<NT> taskClass, String taskId);
	void debugPrintPreviousTasks();

}
