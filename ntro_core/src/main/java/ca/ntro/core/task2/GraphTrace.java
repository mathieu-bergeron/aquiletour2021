package ca.ntro.core.task2;

public interface GraphTrace {

	int size();
	
	void appendGraph(GraphDescription graph);
	GraphDescription getGraph(int index);

}
