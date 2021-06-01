package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageHandlerTask;
import ca.ntro.messages.NtroMessage;
import ca.ntro.services.Ntro;

public abstract class NtroController<AC extends NtroAbstractController> extends NtroAbstractController {

	private AC parentController;

	void setParentController(AC parentController) {
		T.call(this);

		this.parentController = parentController;
	}
	
	public AC getParentController() {
		T.call(this);
		
		return parentController;
	}

	protected void addParentViewMessageHandler(Class<? extends NtroMessage> messageClass, ParentViewMessageHandler<?,?,?> handler) {
		T.call(this);

		String messageId = Ntro.introspector().getSimpleNameForClass(messageClass);
		String handlerId = Ntro.introspector().getSimpleNameForClass(handler.getClass());

		handler.setParentController(parentController);
		handler.setMessageId(messageId);
		handler.getTask().setTaskId(handlerId);

		getTask().addSubTask(handler.getTask());
		addPreviousTaskTo(handler.getTask(), ViewCreatorTask.class, Constants.VIEW_CREATOR_TASK_ID);

		MessageHandlerTask<NtroMessage> messageHandlerTask = Ntro.messages().createMessageHandlerTask(messageClass);
		
		handler.getTask().addPreviousTask(messageHandlerTask);
	}

	protected void addControllerMessageHandler(Class<? extends NtroMessage> messageClass, ControllerMessageHandler<?,?,?> handler) {
		T.call(this);
		
		String messageId = messageClass.getSimpleName();
		
		handler.setController(this);
		handler.setMessageId(messageId);

		getTask().addSubTask(handler.getTask());
		addPreviousTaskTo(handler.getTask(), ViewCreatorTask.class, Constants.VIEW_CREATOR_TASK_ID);

		MessageHandlerTask<NtroMessage> messageHandlerTask = Ntro.messages().createMessageHandlerTask(messageClass);

		handler.getTask().addPreviousTask(messageHandlerTask);
	}
}
