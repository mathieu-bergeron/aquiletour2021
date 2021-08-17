package ca.ntro.core.mvc;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.GraphTraceConnector;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;
import ca.ntro.messages.NtroMessage;

public abstract class   ModelMessageHandler<M extends NtroModel, MSG extends NtroMessage>
                extends Handler<NtroAbstractController>
                implements TaskWrapper  {
	
	private ModelMessageHandlerTask<M,MSG> task;
	
	void setMessageId(String messageId) {
		T.call(this);
		
		task = new ModelMessageHandlerTask<M,MSG>(this, messageId);
	}
	

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

	void handleImpl(M model, MSG message) {
		T.call(this);
		
		handle(model, message);
	}
	
	protected abstract void handle(M model, MSG message);
	
}
