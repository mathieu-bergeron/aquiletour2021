package ca.ntro.core.mvc.view;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;

public class ViewReceptorTask<V extends NtroView> extends NtroTaskImpl {
	
	private ViewReceptor<V> viewReceptor;
	
	public ViewReceptorTask(ViewReceptor<V> viewReceptor) {
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
		V view = (V) ((ViewLoader) getPreviousTask(ViewLoader.class)).createView();

		viewReceptor.onViewLoaded(view);
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
