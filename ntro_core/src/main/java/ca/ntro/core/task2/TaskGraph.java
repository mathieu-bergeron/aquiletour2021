package ca.ntro.core.task2;

public interface TaskGraph {

	void forEachStartNode(NodeLambda lambda);
	void forEachNode(NodeLambda lambda);
	void forEachEdge(EdgeLambda lambda);

	boolean isSameGraphAs(TaskGraph otherGraph);

	void writeGraph(GraphWriter writer);

	GraphDescription getDescription();
}
