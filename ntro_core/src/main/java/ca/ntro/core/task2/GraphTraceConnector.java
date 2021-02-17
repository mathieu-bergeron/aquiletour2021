package ca.ntro.core.task2;

public interface GraphTraceConnector {

	void addGraphWriter(GraphTraceWriter writer);
	void addTaskStateListener(TaskStateListener listener);


}
