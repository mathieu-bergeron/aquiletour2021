package ca.aquiletour.core.pages.dashboards.student;

import ca.aquiletour.core.pages.dashboards.DashboardController;
import ca.aquiletour.core.pages.dashboards.DashboardView;
import ca.aquiletour.core.pages.dashboards.DashboardViewModel;
import ca.aquiletour.core.pages.dashboards.student.messages.ShowStudentDashboardHandler;
import ca.aquiletour.core.pages.dashboards.student.messages.ShowStudentDashboardMessage;
import ca.ntro.core.system.trace.T;

public class DashboardControllerStudent extends DashboardController {

	@Override
	protected Class<? extends DashboardView> viewClass() {
		T.call(this);

		return DashboardViewStudent.class;
	}

	@Override
	protected void installParentViewMessageHandler() {
		T.call(this);
		
		addParentViewMessageHandler(ShowStudentDashboardMessage.class, new ShowStudentDashboardHandler());
		
		addSubViewLoader(DashboardCourseViewStudent.class, context().lang());
		
		addModelViewSubViewHandler(DashboardCourseViewStudent.class, new DashboardViewModel());
	}
}
