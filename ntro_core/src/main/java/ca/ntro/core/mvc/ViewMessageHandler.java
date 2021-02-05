package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;
import ca.ntro.messages.NtroMessage;

public abstract class ViewMessageHandler<V extends NtroView, 
                                         MSG extends NtroMessage> 
                extends Handler
                implements TaskWrapper {

	private NtroTask mainTask = new ViewMessageHandlerTask<V, MSG>(this, "FIXME");

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

	void handleImpl(V view, MSG message) {
		T.call(this);
		
		this.handle(view, message);
	}

	protected abstract void handle(V view, MSG message);
}
