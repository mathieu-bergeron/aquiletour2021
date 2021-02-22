package ca.ntro.core.tasks;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GraphDescriptionImpl implements GraphDescription {
	
	private Set<NodeDescription> nodes = new HashSet<>();
	private Set<EdgeDescription> edges = new HashSet<>();

	@Override
	public void addNode(NodeDescription node) {
		nodes.add(node);
	}

	@Override
	public void addEdge(NodeDescription from, NodeDescription to) {
		edges.add(new EdgeDescriptionImpl(from, to));
	}
	
	
	// XXX: must write parent before child
	//      as cluster must exists in order to add
	//      a subnode to it
	private void writeNode(GraphWriter writer, Set<NodeDescription> visitedNodes, NodeDescription node) {
		if(visitedNodes.contains(node)) return;
		visitedNodes.add(node);
		
		if(node.getParentNode() != null) {
			writeNode(writer, visitedNodes, node.getParentNode());
		}
		
		node.writeNode(writer);
	}

	@Override
	public void write(GraphWriter writer) {
		Set<NodeDescription> visitedNodes = new HashSet<>();
		
		for(NodeDescription node : nodes) {
			writeNode(writer, visitedNodes, node);
		}

		edges.forEach(e -> e.write(writer));
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(nodes, edges);
	}
	
	@Override
	public boolean equals(Object other) {
		if(this == other) return true;
		if(other == null) return false;
		if(other instanceof GraphDescriptionImpl) {

			GraphDescriptionImpl otherGraph = (GraphDescriptionImpl) other;
			
			return nodes.equals(otherGraph.nodes)
					&& edges.equals(otherGraph.edges);
		}
		
		return false;
		
	}


}
