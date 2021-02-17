package ca.ntro.core.task2;

import java.util.HashSet;
import java.util.Set;

public class GraphDescriptionImpl implements GraphDescription {
	
	private Set<NodeDescription> nodes = new HashSet<>();
	private Set<NodeDescription> startNodes = new HashSet<>();
	private Set<EdgeDescription> edges = new HashSet<>();

	@Override
	public void addNode(NodeDescription node) {
		nodes.add(node);
	}
	@Override
	public void addEdge(NodeDescription from, NodeDescription to) {
		edges.add(new EdgeDescriptionImpl(from, to));
	}
	@Override
	public void write(GraphWriter writer) {
		// TODO Auto-generated method stub
		
	}


}
