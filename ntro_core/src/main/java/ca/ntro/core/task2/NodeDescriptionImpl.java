package ca.ntro.core.task2;

public class NodeDescriptionImpl implements NodeDescription {
	
	private String id;
	private String label;
	private boolean isRoot;
	private boolean isCluster;
	private boolean isStartNode;
	
	private NodeDescription parentNode;

	public NodeDescriptionImpl(String id, 
			                   String label, 
			                   boolean isRoot, 
			                   boolean isCluster, 
			                   boolean isStartNode,
			                   NodeDescription parentNode) {
		this.id = id;
		this.label = label;
		this.isRoot = isRoot;
		this.isCluster = isCluster;
		this.isStartNode = isStartNode;
		this.parentNode = parentNode;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public boolean isRoot() {
		return isRoot;
	}

	@Override
	public boolean isNode() {
		return !isCluster;
	}

	@Override
	public boolean isCluster() {
		return isCluster;
	}

	@Override
	public boolean isRootNode() {
		return isRoot() && isNode();
	}

	@Override
	public boolean isRootCluster() {
		return isRoot() && isCluster();
	}
	
	private boolean hasParent() {
		return getParentNode() != null;
	}

	@Override
	public boolean isSubNode() {
		return hasParent() && isNode();
	}

	@Override
	public boolean isSubCluster() {
		return hasParent() && isCluster();
	}

	@Override
	public boolean isStartNode() {
		return isStartNode;
	}

	@Override
	public NodeDescription getParentNode() {
		return parentNode;
	}

	@Override
	public void writeNode(GraphWriter writer) {
		if(isRootNode()) {

			writer.addRootNode(this);

		}else if(isRootCluster()) {

			writer.addRootCluster(this);

		}else if(isSubNode()) {

			writer.addSubNode(getParentNode(), this);

		}else if(isSubCluster()){

			writer.addSubCluster(getParentNode(), this);
		}
	}

}
