package ca.ntro.core.tasks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphTraceImpl implements GraphTrace, GraphTraceConnector {
	
	private Set<TaskStateListener> listeners = new HashSet<>();
	private Set<GraphTraceWriter> writers = new HashSet<>();
	private List<GraphDescription> graphs = new ArrayList<>();
	private List<TaskStateDescription> tasks = new ArrayList<>();

	@Override
	public void addGraphWriter(GraphTraceWriter writer) {
		writers.add(writer);
		
		for(int i = 0; i < graphs.size(); i++) {
			writer.write(i, graphs.get(i));
		}
	}

	@Override
	public boolean append(GraphDescription graph, TaskStateDescription task) {
		// TMP
		if(task.getState() == TaskState.DONE || graphs.size() == 0); else return false;
		
		boolean cycleDetected = false;

		if(graphs.contains(graph)) {
			cycleDetected = true;
		}

		for(GraphTraceWriter writer : writers) {
			writer.write(graphs.size(), graph);
		}

		for(TaskStateListener listener : listeners) {
			listener.onNewTaskState(task);
		}

		tasks.add(task);
		graphs.add(graph);
		
		return cycleDetected;
	}

	@Override
	public void addTaskStateListener(TaskStateListener listener) {
		listeners.add(listener);
		
		for(TaskStateDescription taskStateDescription : tasks) {
			listener.onNewTaskState(taskStateDescription);
		}
	}

}
