package ca.ntro.core.task2;

public interface GraphWriter {
	
	void addCluster(Identifiable cluster);
	void addNode(Identifiable node);
	void addSubCluster(Identifiable cluster, Identifiable subCluster);
	void addSubNode(Identifiable cluster, Identifiable subNode);
	void addEdge(Identifiable from, Identifiable to);

}
