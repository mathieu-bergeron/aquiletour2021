package ca.ntro.core.task2;

public interface TaskGraph {

	void forEachStartNode(NodeLambda lambda);
	void forEachNode(NodeLambda lambda);
	void forEachEdge(EdgeLambda lambda);

	void resetGraph();

	boolean isSameGraphAs(TaskGraph otherGraph);

	GraphDescription getGraphDescription();
}
