package ca.ntro.core.task2;

public interface Node extends NodeDescription {
	
	Node getParentNode();

	void forEachSubNode(NodeLambda lambda);
	void forEachSubNodeTransitive(NodeLambda lambda);

	void forEachNextNode(NodeLambda lambda);
	void forEachNextNodeTransitive(NodeLambda lambda);

	void forEachReachableNode(NodeLambda lambda);
	void forEachReachableNodeTransitive(NodeLambda lambda);
	
	NtroTask asTask();

	NodeDescription getDescription();

}
