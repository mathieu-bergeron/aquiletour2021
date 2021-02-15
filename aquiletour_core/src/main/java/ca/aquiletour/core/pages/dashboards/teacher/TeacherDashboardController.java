package ca.aquiletour.core.pages.dashboards.teacher;

import ca.aquiletour.core.pages.dashboards.DashboardController;
import ca.aquiletour.core.pages.dashboards.DashboardView;
import ca.aquiletour.core.pages.dashboards.teacher.messages.ShowTeacherDashboardHandler;
import ca.aquiletour.core.pages.dashboards.teacher.messages.ShowTeacherDashboardMessage;
import ca.ntro.core.system.trace.T;

public class TeacherDashboardController extends DashboardController {

	@Override
	protected Class<? extends DashboardView> viewClass() {
		T.call(this);

		return TeacherDashboardView.class;
	}

	@Override
	protected void installParentViewMessageHandler() {
		T.call(this);

		addParentViewMessageHandler(ShowTeacherDashboardMessage.class, new ShowTeacherDashboardHandler());
	}
}
