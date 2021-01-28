package ca.aquiletour.core.pages.dashboard;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;

public class ShowDashboardTask extends NtroTaskImpl {

	private DashboardController dashboardController;

	public ShowDashboardTask(DashboardController dashboardController) {
		this.dashboardController = dashboardController;
	}

	@Override
	protected void initializeTask() {
		T.call(this);
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);

		dashboardController.showDashboard();

		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
