package ca.ntro.core.mvc.view;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;

public abstract class ViewReceptor<V extends NtroView> implements TaskWrapper {
	
	private NtroTask mainTask = new ViewReceptorTask<V>(this);

	@Override
	public NtroTask getTask() {
		T.call(this);
		
		return mainTask;
	}

	@Override
	public void execute() {
		T.call(this);
		
		mainTask.execute();
	}

	public abstract void onViewLoaded(V view);
}
