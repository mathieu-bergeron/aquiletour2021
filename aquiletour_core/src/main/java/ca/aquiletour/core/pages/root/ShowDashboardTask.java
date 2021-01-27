package ca.aquiletour.core.pages.root;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;

public class ShowDashboardTask extends NtroTaskImpl {

	private RootController rootController;

	public ShowDashboardTask(RootController rootController) {
		this.rootController = rootController;
	}

	@Override
	protected void initializeTask() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		
		rootController.showDashboard();
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
