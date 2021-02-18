package ca.ntro.core.tasks;

public interface Node extends NodeDescription {
	
	Node getParentNode();

	void forEachSubNode(NodeLambda lambda);
	void forEachSubNodeTransitive(NodeLambda lambda);

	void forEachNextNode(NodeLambda lambda);
	void forEachNextNodeTransitive(NodeLambda lambda);

	void forEachReachableNode(NodeLambda lambda);
	void forEachReachableNodeTransitive(NodeLambda lambda);

	void resetNodeTransitive();
	
	NtroTask asTask();

	NodeDescription getNodeDescription();
}
