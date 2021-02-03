package ca.aquiletour.core.pages.dashboard.messages;

import ca.aquiletour.core.pages.dashboard.DashboardController;
import ca.ntro.core.mvc.MessageReceptor;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;

public class DeleteCourseReceptor extends MessageReceptor {

	private DashboardController dashboardController;

	public DeleteCourseReceptor(DashboardController dashboardController) {
		this.dashboardController = dashboardController;
	}

	@Override
	protected void initializeTask() {
		T.call(this);
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);

		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
