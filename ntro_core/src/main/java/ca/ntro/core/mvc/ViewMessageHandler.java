package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;
import ca.ntro.messages.NtroMessage;

public abstract class ViewMessageHandler<CB extends ControllerBase, 
                                         V extends NtroView, 
                                         MSG extends NtroMessage> 
                extends Handler
                implements TaskWrapper {

	private NtroTask mainTask = new ViewMessageHandlerTask<CB, V, MSG>(this, "FIXME");

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
	void handle(V view, MSG message) {
		T.call(this);
		
		this.handle((CB) getController(), view, message);
	}

	protected abstract void handle(CB controller, V view, MSG message);
}
