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
		
		String messageId = messageClass.getSimpleName();
		
		handler.setParentController(parentController);
		handler.setMessageId(messageId);

		getTask().addSubTask(handler.getTask());
		addPreviousTaskTo(handler.getTask(), ViewCreatorTask.class, Constants.VIEW_CREATOR_TASK_ID);

		NtroMessage message = MessageFactory.getIncomingMessage(messageClass);
		message.setTaskId(messageId);

		handler.getTask().addPreviousTask(message);
	}
}
