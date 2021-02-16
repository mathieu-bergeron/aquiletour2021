package ca.ntro.core.task2;

public class SimpleNode implements Node {

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node getParentNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRoot() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCluster() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRootNode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRootCluster() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSubNode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSubCluster() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStartNode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void forEachSubNode(NodeLambda lambda) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void forEachSubNodeTransitive(NodeLambda lambda) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void forEachNextNode(NodeLambda lambda) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void forEachNextNodeTransitive(NodeLambda lambda) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void forEachReachableNode(NodeLambda lambda) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void forEachReachableNodeTransitive(NodeLambda lambda) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeNode(GraphWriter writer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NtroTask asTask() {
		// TODO Auto-generated method stub
		return null;
	}

}
