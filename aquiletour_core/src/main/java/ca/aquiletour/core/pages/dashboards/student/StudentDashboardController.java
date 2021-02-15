package ca.aquiletour.core.pages.dashboards.student;

import ca.aquiletour.core.pages.dashboards.DashboardController;
import ca.aquiletour.core.pages.dashboards.DashboardView;
import ca.aquiletour.core.pages.dashboards.student.messages.ShowStudentDashboardHandler;
import ca.aquiletour.core.pages.dashboards.student.messages.ShowStudentDashboardMessage;
import ca.ntro.core.system.trace.T;

public class StudentDashboardController extends DashboardController {

	@Override
	protected Class<? extends DashboardView> viewClass() {
		T.call(this);

		return StudentDashboardView.class;
	}

	@Override
	protected void installParentViewMessageHandler() {
		T.call(this);

		addParentViewMessageHandler(ShowStudentDashboardMessage.class, new ShowStudentDashboardHandler());
	}
}
