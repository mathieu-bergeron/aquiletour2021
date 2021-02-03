package ca.aquiletour.core.pages.dashboard.messages;

import ca.aquiletour.core.pages.dashboard.DashboardController;
import ca.ntro.core.mvc.MessageReceptor;
import ca.ntro.core.system.trace.T;

public class AddCourseReceptor extends MessageReceptor {

	private DashboardController dashboardController;

	public AddCourseReceptor(DashboardController dashboardController) {
		this.dashboardController = dashboardController;
	}

	@Override
	protected void initializeTask() {
		T.call(this);
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		
		AddCourseMessage message = (AddCourseMessage) getMessage();
		
		dashboardController.addCourse(message.getCourse());

		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
