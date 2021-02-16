package ca.ntro.core.task2;

import java.util.HashSet;
import java.util.Set;

public class SimpleTaskGraph implements TaskGraph {
	
	private Set<Node> nodes = new HashSet<>();
	private Set<Node> startNodes = new HashSet<>();
	private Set<Edge> edges = new HashSet<>();

	@Override
	public void forEachStartNode(NodeLambda lambda) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void forEachNode(NodeLambda lambda) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void forEachEdge(EdgeLambda lambda) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSameGraphAs(TaskGraph otherGraph) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void writeGraph(GraphWriter writer) {
		// TODO Auto-generated method stub
		
	}

}
