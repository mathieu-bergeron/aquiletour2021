package ca.ntro.core.mvc;

import ca.ntro.core.system.assertions.MustNot;


import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskAsync;
import ca.ntro.messages.NtroMessage;

import static ca.ntro.core.mvc.Constants.VIEW_LOADER_TASK_ID;

public class ViewMessageHandlerTask<V extends NtroView, 
                                    MSG extends NtroMessage> extends NtroTaskAsync {
	
	private ViewMessageHandler<V, MSG> handler;
	private String messageId;
	
	public ViewMessageHandlerTask(ViewMessageHandler<V, MSG> handler, String messageId) {
		T.call(this);

		this.handler = handler;
		this.messageId = messageId;
	}

	@Override
	protected void initializeTask() {
		T.call(this);
		
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		
		ViewLoader viewLoader = (ViewLoader) getPreviousTask(ViewLoader.class, VIEW_LOADER_TASK_ID);
		MSG message = (MSG) getPreviousTask(NtroMessage.class, messageId);

		MustNot.beNull(viewLoader);
		MustNot.beNull(message);

		@SuppressWarnings("unchecked")
		V view = (V) viewLoader.createView();
		
		MustNot.beNull(view);

		handler.handleImpl(view, message);
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
