package ca.ntro.core.tasks;

public interface GraphDescription {

	/**
	 * IMPORTANT: nodes must be added in a traversable
	 *            order
	 */
	void addNode(NodeDescription node);
	void addEdge(NodeDescription from, NodeDescription to);

	void write(GraphWriter writer);

}
