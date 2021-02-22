package ca.ntro.core.tasks;

public interface GraphWriter {
	
	/**
	 *  IMPORTANT: nodes must be added in a traversable order
	 * 
	 * @param cluster
	 */
	void addRootCluster(NodeDescription cluster);
	void addRootNode(NodeDescription node);
	void addSubCluster(NodeDescription cluster, NodeDescription subCluster);
	void addSubNode(NodeDescription cluster, NodeDescription subNode);
	void addEdge(NodeDescription from, NodeDescription to);

}
