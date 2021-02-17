package ca.ntro.core.task2;

import java.util.ArrayList;
import java.util.List;

public class GraphDescriptionImpl implements GraphDescription {
	
	// XXX: we assume nodes are inserted in a traversable order
	private List<NodeDescription> nodes = new ArrayList<>();
	private List<EdgeDescription> edges = new ArrayList<>();

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
		nodes.forEach(n -> n.writeNode(writer));
		edges.forEach(e -> e.write(writer));
	}


}
