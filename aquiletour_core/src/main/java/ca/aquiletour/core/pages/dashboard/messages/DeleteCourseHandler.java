package ca.aquiletour.core.pages.dashboard.messages;

import ca.aquiletour.core.pages.dashboard.DashboardController;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;
import ca.ntro.messages.MessageHandler;

public class DeleteCourseHandler extends MessageHandler {

	private DashboardController dashboardController;

	public DeleteCourseHandler(DashboardController dashboardController) {
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
