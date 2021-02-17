package ca.ntro.core.task2;

public interface NodeDescription extends Identifiable, Labellable {

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
