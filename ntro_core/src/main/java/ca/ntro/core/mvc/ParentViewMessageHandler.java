package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.GraphTraceConnector;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;
import ca.ntro.messages.NtroMessage;

public abstract class ParentViewMessageHandler<PV extends NtroView, 
                                               CV extends NtroView, 
                                               MSG extends NtroMessage> 
                implements TaskWrapper {
	
	private ParentViewMessageHandlerTask<PV,CV,MSG> task;
	
	void setMessageId(String messageId) {
		T.call(this);

		task = new ParentViewMessageHandlerTask<PV,CV,MSG>(this, messageId);
	}
	
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
	public GraphTraceConnector execute() {
		T.call(this);
		
		return task.execute();
	}

	@SuppressWarnings("unchecked")
	public void handleImpl(CV currentView, MSG message) {
		T.call(this);
		
		handle((PV)parentController.getView(), currentView, message);
	}
	
	protected abstract void handle(PV parentView, CV currentView, MSG message);

}
