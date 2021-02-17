package ca.ntro.core.task2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphTraceImpl implements GraphTrace, GraphTraceConnector {
	
	private Set<GraphTraceWriter> writers = new HashSet<>();
	private List<GraphDescription> graphs = new ArrayList<>();

	@Override
	public void addWriter(GraphTraceWriter writer) {
		writers.add(writer);
		
		for(int i = 0; i < graphs.size(); i++) {
			writer.write(i, graphs.get(i));
		}
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void appendGraph(GraphDescription graph) {
		for(GraphTraceWriter writer : writers) {
			writer.write(graphs.size(), graph);
		}

		graphs.add(graph);
	}

	@Override
	public GraphDescription getGraph(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GraphTrace getTrace() {
		return this;
	}

}
