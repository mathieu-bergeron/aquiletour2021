package ca.ntro.core.task2;

public interface GraphDescription {

	void addNode(NodeDescription node);
	void addEdge(NodeDescription from, NodeDescription to);

	void write(GraphWriter writer);

}
