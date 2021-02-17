package ca.ntro.core.task2;

public class EdgeDescriptionImpl implements EdgeDescription {
	
	private NodeDescription from;
	private NodeDescription to;
	
	public EdgeDescriptionImpl() {
	}

	public EdgeDescriptionImpl(NodeDescription from, NodeDescription to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public NodeDescription getFrom() {
		return from;
	}

	public void setFrom(NodeDescription from) {
		this.from = from;
	}


	@Override
	public NodeDescription getTo() {
		return to;
	}

	public void setTo(NodeDescription to) {
		this.to = to;
	}
	
	
	
	


}
