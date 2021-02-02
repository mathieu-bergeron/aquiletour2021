package ca.ntro.core.models;

import ca.ntro.core.mvc.view.NtroView;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;

public abstract class NtroViewModel extends NtroTaskImpl {

	public abstract void observeAndDisplay(NtroModel model, NtroView view);

	@Override
	protected void initializeTask() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);

		observeAndDisplay(null, null);
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}
	

}
