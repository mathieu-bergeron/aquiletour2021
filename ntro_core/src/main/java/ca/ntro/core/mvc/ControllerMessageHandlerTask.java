package ca.ntro.core.mvc;

import ca.ntro.core.system.assertions.MustNot;


import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskAsync;
import ca.ntro.messages.MessageHandlerTask;
import ca.ntro.messages.NtroMessage;

import static ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID;

class ControllerMessageHandlerTask<C extends NtroAbstractController, CV extends NtroView, MSG extends NtroMessage> extends NtroTaskAsync {
	
	
	private ControllerMessageHandler<C,CV,MSG> handler;
	private String messageId;
	
	public ControllerMessageHandlerTask(ControllerMessageHandler<C,CV,MSG> handler, String messageId) {
		T.call(this);

		this.handler = handler;
		this.messageId = messageId;
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		
		@SuppressWarnings("unchecked")
		CV currentView = (CV) ((ViewCreatorTask) getPreviousTask(ViewCreatorTask.class, VIEW_CREATOR_TASK_ID)).getView();
		MustNot.beNull(currentView);

		@SuppressWarnings("unchecked")
		MessageHandlerTask messageHandler = (MessageHandlerTask) getPreviousTask(MessageHandlerTask.class, messageId);

		handler.handleImpl(currentView, (MSG) messageHandler.getMessage());
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
