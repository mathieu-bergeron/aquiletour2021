package ca.ntro.core.tasks;

public interface TaskGraph {

	void forEachStartNode(NodeLambda lambda);
	void forEachNode(NodeLambda lambda);
	void forEachEdge(EdgeLambda lambda);

	Node findNodeById(String id);

	void resetGraph();
	
	boolean isSameGraphAs(TaskGraph otherGraph);

	GraphDescription getGraphDescription();
}
