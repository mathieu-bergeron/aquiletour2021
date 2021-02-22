package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.GraphTraceConnector;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;

public  abstract class WindowViewHandler<V extends NtroView> implements TaskWrapper {

	private NtroWindow window;
	private WindowViewHandlerTask<V> task = new WindowViewHandlerTask<V>(this);
	
	void setWindow(NtroWindow window) {
		T.call(this);
		
		this.window = window;
	}

	void handleImpl(V view) {
		T.call(this);
		
		handle(window, view);
	}

	public abstract void handle(NtroWindow window, V view);

	@Override
	public NtroTask getTask() {
		T.call(this);
		
		return task;
	}

	@Override
	public GraphTraceConnector execute() {
		T.call(this);
		
		return task.execute();
	}
}
