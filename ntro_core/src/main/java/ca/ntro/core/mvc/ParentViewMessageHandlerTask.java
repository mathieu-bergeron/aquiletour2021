package ca.ntro.core.mvc;

import ca.ntro.core.system.assertions.MustNot;


import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;
import ca.ntro.messages.NtroMessage;

import static ca.ntro.core.mvc.Constants.VIEW_LOADER_TASK_ID;

class ParentViewMessageHandlerTask<PV extends NtroView, 
                                   CV extends NtroView, 
                                   MSG extends NtroMessage> extends NtroTaskImpl {
	
	
	private ParentViewMessageHandler<PV,CV,MSG> handler;
	
	public ParentViewMessageHandlerTask(ParentViewMessageHandler<PV,CV,MSG> handler) {
		T.call(this);

		this.handler = handler;
	}

	@Override
	protected void initializeTask() {
		T.call(this);
		
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		
		ViewLoader viewLoader = (ViewLoader) getPreviousTask(ViewLoader.class, VIEW_LOADER_TASK_ID);
		MSG message = (MSG) getPreviousTask(NtroMessage.class, "FIXME");

		MustNot.beNull(viewLoader);

		@SuppressWarnings("unchecked")
		CV currentView	 = (CV) viewLoader.getView();
		
		handler.handleImpl(currentView, message);
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
