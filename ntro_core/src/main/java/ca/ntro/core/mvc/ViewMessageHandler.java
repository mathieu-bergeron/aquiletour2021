package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.GraphTraceConnector;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;
import ca.ntro.messages.NtroMessage;

public abstract class ViewMessageHandler<V extends NtroView, 
                                         MSG extends NtroMessage> 
                extends Handler
                implements TaskWrapper {

	private ViewMessageHandlerTask<V,MSG> mainTask;

	public void setMessageId(String messageId) {
		T.call(this);

		mainTask = new ViewMessageHandlerTask<V, MSG>(this, messageId);
	}

	@Override
	public NtroTask getTask() {
		T.call(this);
		
		return mainTask;
	}

	@Override
	public GraphTraceConnector execute() {
		T.call(this);
		
		return mainTask.execute();
		
	}

	void handleImpl(V view, MSG message) {
		T.call(this);
		
		this.handle(view, message);
	}

	protected abstract void handle(V view, MSG message);

}
