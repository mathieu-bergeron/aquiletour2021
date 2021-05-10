package ca.ntro.core.tasks;

public class GraphTraceEmpty implements GraphTrace, GraphTraceConnector {

	@Override
	public void addGraphWriter(GraphTraceWriter writer) {
	}

	@Override
	public boolean append(GraphDescription graph, TaskStateDescription task) {
		return false;
	}

	@Override
	public void addTaskStateListener(TaskStateListener listener) {
	}
}
