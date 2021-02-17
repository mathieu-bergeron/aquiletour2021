package ca.ntro.core.task2;

public interface EdgeDescription {
	
	NodeDescription getFrom();
	NodeDescription getTo();
	
	void write(GraphWriter writer);
}
