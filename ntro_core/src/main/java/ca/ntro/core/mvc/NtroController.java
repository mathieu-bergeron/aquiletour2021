package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageFactory;
import ca.ntro.messages.NtroMessage;

public abstract class NtroController<AC extends NtroAbstractController> extends NtroAbstractController {
	
	private AC parentController;
	
	void setParentController(AC parentController) {
		T.call(this);
		
		this.parentController = parentController;
	}

	protected void addParentViewMessageHandler(Class<? extends NtroMessage> messageClass, ParentViewMessageHandler<?,?,?> handler) {
		T.call(this);
		
		handler.setParentController(parentController);
		
		NtroMessage message = MessageFactory.getIncomingMessage(messageClass);
		message.setTaskId(Constants.MESSAGE_TASK_ID);
		handler.getTask().addPreviousTask(message);

		getTask().addSubTask(handler.getTask());
		addPreviousTaskTo(handler.getTask(), ViewLoader.class, Constants.VIEW_LOADER_TASK_ID);
	}
}
