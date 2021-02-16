package ca.ntro.core.task2;

public interface TaskGraphNode extends NodeSpec {
	
	boolean isRoot();
	
	boolean isNode();
	boolean isCluster();

	boolean isRootNode();
	boolean isRootCluster();

	boolean isSubNode();
	boolean isSubCluster();
	
	boolean isStartNode();

	void writeNode(GraphWriter writer);
	
	NtroTask asTask();

}
