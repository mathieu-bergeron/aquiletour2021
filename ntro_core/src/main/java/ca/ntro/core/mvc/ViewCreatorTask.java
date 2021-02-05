package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskAsync;

public class ViewCreatorTask extends NtroTaskAsync {
	
	private NtroView view;
	
	NtroView getView() {
		T.call(this);
		return view;
	}

	@Override
	protected void initializeTask() {
	}

	@Override
	protected void runTaskAsync() {
		view = ((ViewLoader) getPreviousTask(ViewLoader.class, Constants.VIEW_LOADER_TASK_ID)).createView();
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}
	

}
