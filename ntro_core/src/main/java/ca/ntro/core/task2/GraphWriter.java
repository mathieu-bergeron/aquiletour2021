package ca.ntro.core.task2;

public interface GraphWriter {

	void addCluster(Identifiable cluster);
	void addNode(Identifiable node);
	void addNodeToCluster(Identifiable cluster, Identifiable node);
	void addClusterToCluster(Identifiable parentCluster, Identifiable childCluster);
	void addEdge(Identifiable from, Identifiable to);

}
