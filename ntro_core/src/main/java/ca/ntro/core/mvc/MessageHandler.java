package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.GraphTraceConnector;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;
import ca.ntro.messages.NtroMessage;

public abstract class   MessageHandler<C extends AnyController, 
                                       MSG extends NtroMessage>
                extends Handler<C>
                implements TaskWrapper  {
	
	private MessageHandlerTask<C,MSG> task;
	
	void setMessageId(String messageId) {
		T.call(this);
		
		task = new MessageHandlerTask<C,MSG>(this, messageId);
		task.setTaskId(messageId);
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

	void handleImpl(MSG message) {
		T.call(this);
		
		handle(message);
	}
	
	protected abstract void handle(MSG message);
	
}
