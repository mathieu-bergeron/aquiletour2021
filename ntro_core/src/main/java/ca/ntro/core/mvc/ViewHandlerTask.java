package ca.ntro.core.mvc;

import ca.ntro.core.system.assertions.MustNot;


import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;

import static ca.ntro.core.mvc.Constants.VIEW_LOADER_TASK_ID;

public class ViewHandlerTask<CB extends ControllerBase, V extends NtroView> extends NtroTaskImpl {
	
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
		
		ViewLoader viewLoader = (ViewLoader) getPreviousTask(ViewLoader.class, VIEW_LOADER_TASK_ID);

		MustNot.beNull(viewLoader);

		@SuppressWarnings("unchecked")
		V view = (V) viewLoader.createView();
		
		MustNot.beNull(view);

		viewReceptor.handle(view);
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
