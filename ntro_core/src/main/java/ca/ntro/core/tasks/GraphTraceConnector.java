package ca.ntro.core.tasks;

public interface GraphTraceConnector {

	void addGraphWriter(GraphTraceWriter writer);
	void addTaskStateListener(TaskStateListener listener);


}
