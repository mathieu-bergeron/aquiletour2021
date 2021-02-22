package ca.ntro.core.tasks;

public interface EdgeDescription {
	
	NodeDescription getFrom();
	NodeDescription getTo();
	
	void write(GraphWriter writer);
}
