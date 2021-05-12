package ca.aquiletour.core.pages.dashboard.student;

import ca.aquiletour.core.pages.dashboard.DashboardController;
import ca.aquiletour.core.pages.dashboard.handlers.DashboardViewModel;
import ca.aquiletour.core.pages.dashboard.models.Dashboard;
import ca.aquiletour.core.pages.dashboard.student.handlers.ShowStudentDashboardHandler;
import ca.aquiletour.core.pages.dashboard.student.messages.ShowStudentDashboardMessage;
import ca.aquiletour.core.pages.dashboard.student.models.DashboardStudent;
import ca.aquiletour.core.pages.dashboard.student.views.DashboardCourseViewStudent;
import ca.aquiletour.core.pages.dashboard.student.views.DashboardViewStudent;
import ca.aquiletour.core.pages.dashboard.views.DashboardView;
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

	@Override
	protected Class<? extends Dashboard> modelClass() {
		T.call(this);
		
		return DashboardStudent.class;
	}
}
