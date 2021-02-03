package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;
import ca.ntro.messages.NtroMessage;

public abstract class ParentViewMessageHandler<PV extends NtroView, 
                                               CV extends NtroView, 
                                               MSG extends NtroMessage> 
                implements TaskWrapper {
	
	private ParentViewMessageHandlerTask<PV,CV,MSG> task = new ParentViewMessageHandlerTask<PV,CV,MSG>(this);
	
	private NtroAbstractController parentController;
	
	void setParentController(NtroAbstractController parentController) {
		T.call(this);
		
		this.parentController = parentController;
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
		
		handle((PV)parentController.getView(), currentView, message);
	}
	
	protected abstract void handle(PV parentView, CV currentView, MSG message);

}
