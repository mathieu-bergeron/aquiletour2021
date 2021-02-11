package ca.ntro.core.task2;

import java.util.Set;

public interface NtroTask extends NodeSpec {

	void setId(String id);
	void setParentTask(NtroTask parentTask);

	boolean hasParent();
	boolean hasSubTasks();
	NtroTask getParentTask();

	boolean isSubCluster();
	boolean isSubNode();
	boolean isRootCluster();
	boolean isRootNode();
	
	void addSubTask(NtroTask task);
	void addSubTask(NtroTask task, String taskId);

	void addNextTask(NtroTask task);
	void addNextTask(NtroTask task, String taskId);

	void addPreviousTask(NtroTask task);
	void addPreviousTask(NtroTask task, String taskId);

	void execute();
	void execute(GraphTraceWriter writer);

	void writeGraph(GraphWriter writer);
	void write(GraphWriter writer);
}