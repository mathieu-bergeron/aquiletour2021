package ca.ntro.core.task2;

import java.util.Set;

public interface NtroTask extends Identifiable {

	void setId(String id);
	
	void addSubTask(NtroTask task);
	void addSubTask(NtroTask task, String taskId);

	void addNextTask(NtroTask task);
	void addNextTask(NtroTask task, String taskId);

	void addPreviousTask(NtroTask task);
	void addPreviousTask(NtroTask task, String taskId);

	void execute();
	void execute(GraphTraceWriter writer);

	void writeGraph(GraphWriter writer);
	void writeGraph(GraphWriter writer, Set<NtroTask> visitedTasks);
}