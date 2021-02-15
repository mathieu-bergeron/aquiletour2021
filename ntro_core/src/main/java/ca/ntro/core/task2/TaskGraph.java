package ca.ntro.core.task2;

public interface TaskGraph {

	void forEachStartNode(TaskLambda lambda);
	void forEachNode(TaskLambda lambda);
	void forEachEdge(EdgeLambda lambda);

	void writeGraph(GraphWriter writer);
}
