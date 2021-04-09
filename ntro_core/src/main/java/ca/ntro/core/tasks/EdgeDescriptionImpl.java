package ca.ntro.core.tasks;

import java.util.Objects;

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

	@Override
	public void write(GraphWriter writer) {
		writer.addEdge(from, to);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(from, to);
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null) return false;
		if(this == other) return true;
		if(other instanceof EdgeDescriptionImpl) {
			
			EdgeDescriptionImpl otherEdge = (EdgeDescriptionImpl) other;
			
			return from.equals(otherEdge.from) 
					&& to.equals(otherEdge.to);
		}
		
		return false;
	}
	
	
	
}
