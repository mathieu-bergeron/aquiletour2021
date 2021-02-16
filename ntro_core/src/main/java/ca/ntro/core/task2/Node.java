package ca.ntro.core.task2;

public interface Node extends NodeSpec {
	
	Node getParentNode();
	
	boolean isRoot();
	
	boolean isNode();
	boolean isCluster();

	boolean isRootNode();
	boolean isRootCluster();

	boolean isSubNode();
	boolean isSubCluster();
	
	boolean isStartNode();

	void forEachSubNode(NodeLambda lambda);
	void forEachSubNodeTransitive(NodeLambda lambda);

	void forEachNextNode(NodeLambda lambda);
	void forEachNextNodeTransitive(NodeLambda lambda);

	void forEachReachableNode(NodeLambda lambda);
	void forEachReachableNodeTransitive(NodeLambda lambda);

	void writeNode(GraphWriter writer);
	
	NtroTask asTask();

}
