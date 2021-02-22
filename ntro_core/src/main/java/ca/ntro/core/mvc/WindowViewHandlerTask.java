package ca.ntro.core.mvc;

import ca.ntro.core.system.assertions.MustNot;


import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.NtroTaskAsync;
import ca.ntro.messages.NtroMessage;

import static ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID;

public class WindowViewHandlerTask<V extends NtroView> extends NtroTaskAsync{
	
	private WindowViewHandler<V> handler;
	private V view;
	
	public WindowViewHandlerTask(WindowViewHandler handler) {
		T.call(this);

		this.handler = handler;
	}
	
	@Override
	protected void onSomePreviousTaskFinished(String taskId, NtroTask previousTask) {
		T.call(this);
		
		T.here();

		if(previousTask instanceof ViewCreatorTask) {
			view = (V) ((ViewCreatorTask) previousTask).getView();
		}
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		
		/*
		ViewCreatorTask viewCreatorTask = (ViewCreatorTask) getPreviousTask(ViewCreatorTask.class, VIEW_CREATOR_TASK_ID);
		MustNot.beNull(viewCreatorTask);

		@SuppressWarnings("unchecked")
		V view = (V) viewCreatorTask.getView();
		*/
		
		MustNot.beNull(view);

		handler.handleImpl(view);
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
