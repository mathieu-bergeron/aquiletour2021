package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;

public abstract class ViewReceptor<CB extends ControllerBase, V extends NtroView> implements TaskWrapper {
	
	private ControllerBase controller;
	private NtroTask mainTask = new ViewReceptorTask<CB, V>(this);
	
	public void setController(ControllerBase controller) {
		T.call(this);
		
		this.controller = controller;
	}

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
	
	@SuppressWarnings("unchecked")
	void onViewLoaded(V view) {
		T.call(this);
		
		onViewLoaded((CB) controller, view);
	}

	public abstract void onViewLoaded(CB controller, V view);
}
