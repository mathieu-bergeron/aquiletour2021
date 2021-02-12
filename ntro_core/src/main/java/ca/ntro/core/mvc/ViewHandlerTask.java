package ca.ntro.core.mvc;

import ca.ntro.core.system.assertions.MustNot;


import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskAsync;

import static ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID;;

public class ViewHandlerTask<CB extends NtroAbstractController, V extends NtroView> extends NtroTaskAsync {
	
	private ViewHandler<CB, V> viewReceptor;
	
	public ViewHandlerTask(ViewHandler<CB, V> viewReceptor) {
		T.call(this);

		this.viewReceptor = viewReceptor;
	}

	@Override
	protected void initializeTask() {
		T.call(this);
		
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		
		@SuppressWarnings("unchecked")
		V view = (V) ((ViewCreatorTask) getPreviousTask(ViewCreatorTask.class, VIEW_CREATOR_TASK_ID)).getView();
		
		MustNot.beNull(view);

		viewReceptor.handle(view);
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
