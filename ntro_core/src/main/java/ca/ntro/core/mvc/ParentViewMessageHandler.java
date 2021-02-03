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
	
	private PV parentView;
	
	@SuppressWarnings("unchecked")
	void setParentView(NtroView parentView) {
		T.call(this);

		this.parentView = (PV) parentView;
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

	public void handleImpl(CV currentView, MSG message) {
		T.call(this);
		
		handle(parentView, currentView, message);
	}
	
	protected abstract void handle(PV parentView, CV currentView, MSG message);

}
