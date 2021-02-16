package ca.ntro.core.task2;

public interface GraphTrace {

	int size();
	
	void appendGraph(TaskGraph graph);
	TaskGraph getGraph(int index);

}
