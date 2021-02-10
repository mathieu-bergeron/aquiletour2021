package ca.ntro.core.models;

import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskAsync;

public abstract class NtroViewModel extends NtroTaskAsync {

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
