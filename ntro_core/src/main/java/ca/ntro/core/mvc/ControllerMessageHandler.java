package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;
import ca.ntro.messages.NtroMessage;

public abstract class ControllerMessageHandler<C extends NtroAbstractController,
											   CV extends NtroView, 
                                               MSG extends NtroMessage> 
                implements TaskWrapper {
	
	private ControllerMessageHandlerTask<C,CV,MSG> task;
	
	void setMessageId(String messageId) {
		T.call(this);

		task = new ControllerMessageHandlerTask<C,CV,MSG>(this, messageId);
	}
	
	private NtroController<?> controller;
	
	void setController(NtroController<?> controller) {
		T.call(this);

		this.controller = controller;
	}

	@Override
	public NtroTask getTask() {
		T.call(this);
		
		return task;
	}

	@Override
	public void execute() {
		T.call(this);
		
		task.execute();
	}

	@SuppressWarnings("unchecked")
	public void handleImpl(CV currentView, MSG message) {
		T.call(this);
		
		handle((C)controller, currentView, message);
	}
	
	protected abstract void handle(C currentController, CV currentView, MSG message);

}
