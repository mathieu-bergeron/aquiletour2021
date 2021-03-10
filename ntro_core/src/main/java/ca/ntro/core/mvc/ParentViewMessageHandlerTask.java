package ca.ntro.core.mvc;

import ca.ntro.core.system.assertions.MustNot;


import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskAsync;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.MessageHandlerTask;

import static ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID;

class ParentViewMessageHandlerTask<PV extends NtroView, 
                                   CV extends NtroView, 
                                   MSG extends NtroMessage> extends NtroTaskAsync {
	
	
	private ParentViewMessageHandler<PV,CV,MSG> handler;
	private String messageId;
	
	public ParentViewMessageHandlerTask(ParentViewMessageHandler<PV,CV,MSG> handler, String messageId) {
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
		MessageHandlerTask messageHandlerTask = (MessageHandlerTask) getPreviousTask(MessageHandlerTask.class, messageId);
		MustNot.beNull(messageHandlerTask);

		handler.handleImpl(currentView, (MSG) messageHandlerTask.getMessage());
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
