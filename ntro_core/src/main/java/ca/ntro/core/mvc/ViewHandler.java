package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;

public abstract class ViewHandler<CB extends ControllerBase, V extends NtroView> extends Handler implements TaskWrapper {
	
	private NtroTask mainTask = new ViewHandlerTask<CB, V>(this);

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
	void handle(V view) {
		T.call(this);
		
		handle((CB) getController(), view);
	}

	public abstract void handle(CB controller, V view);
}
