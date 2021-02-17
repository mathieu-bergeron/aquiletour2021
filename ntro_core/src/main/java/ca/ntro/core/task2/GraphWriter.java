package ca.ntro.core.task2;

public interface GraphWriter {
	
	void addRootCluster(NodeDescription cluster);
	void addRootNode(NodeDescription node);
	void addSubCluster(NodeDescription cluster, NodeDescription subCluster);
	void addSubNode(NodeDescription cluster, NodeDescription subNode);
	void addEdge(NodeDescription from, NodeDescription to);

}
