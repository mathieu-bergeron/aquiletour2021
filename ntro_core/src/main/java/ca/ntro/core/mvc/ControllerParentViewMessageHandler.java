package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;
import ca.ntro.messages.NtroMessage;

public abstract class ControllerParentViewMessageHandler<C extends NtroController<?>, 
												         PV extends NtroView, 
                                                         CV extends NtroView, 
                                                         MSG extends NtroMessage> 
                implements TaskWrapper {
	
	private ControllerParentViewMessageHandlerTask<C,PV,CV,MSG> task;
	
	void setMessageId(String messageId) {
		T.call(this);

		task = new ControllerParentViewMessageHandlerTask<C, PV,CV,MSG>(this, messageId);
	}
	
	private NtroAbstractController parentController;
	
	void setParentController(NtroAbstractController parentController) {
		T.call(this);
		
		this.parentController = parentController;

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
		
		handle((C) controller, (PV)parentController.getView(), currentView, message);
	}
	
	protected abstract void handle(C controller, PV parentView, CV currentView, MSG message);

}
