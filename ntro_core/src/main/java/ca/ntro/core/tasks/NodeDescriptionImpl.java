package ca.ntro.core.tasks;

import java.util.Objects;

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
	public String getNodeId() {
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
	
	@Override
	public int hashCode() {
		return Objects.hash(id, label, parentNode);
	}

	@Override
	public boolean equals(Object other) {
		if(this == other) return true;
		if(other == null) return false;
		if(other instanceof NodeDescriptionImpl) {

			NodeDescriptionImpl otherNode = (NodeDescriptionImpl) other;

			boolean parentEqual = false;
			if(parentNode == null && otherNode.parentNode == null) {
				parentEqual = true;
			}else if(parentNode != null) {
				parentEqual = parentNode.equals(otherNode.parentNode);
			}
			
			return id.equals(otherNode.id)
					&& label.equals(otherNode.label)
					&& parentEqual;
		}
		
		return false;
		
	}

}
