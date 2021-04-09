package ca.ntro.core.tasks;

public interface NodeDescription extends Labellable {
	
	String getNodeId();

	boolean isRoot();
	
	boolean isNode();
	boolean isCluster();

	boolean isRootNode();
	boolean isRootCluster();

	boolean isSubNode();
	boolean isSubCluster();
	
	boolean isStartNode();
	
	NodeDescription getParentNode();

	void writeNode(GraphWriter writer);

}
