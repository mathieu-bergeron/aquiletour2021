package ca.ntro.core.task2;

public interface GraphWriter {
	
	void addRootCluster(NodeSpec cluster);
	void addRootNode(NodeSpec node);
	void addSubCluster(NodeSpec cluster, NodeSpec subCluster);
	void addSubNode(NodeSpec cluster, NodeSpec subNode);
	void addEdge(NodeSpec from, NodeSpec to);

}
