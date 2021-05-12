package ca.aquiletour.core.pages.dashboard.teacher;

import ca.aquiletour.core.pages.dashboard.DashboardController;
import ca.aquiletour.core.pages.dashboard.handlers.DashboardViewModel;
import ca.aquiletour.core.pages.dashboard.models.Dashboard;
import ca.aquiletour.core.pages.dashboard.teacher.handlers.ShowTeacherDashboardHandler;
import ca.aquiletour.core.pages.dashboard.teacher.messages.ShowTeacherDashboardMessage;
import ca.aquiletour.core.pages.dashboard.teacher.models.DashboardTeacher;
import ca.aquiletour.core.pages.dashboard.teacher.views.DashboardCourseViewTeacher;
import ca.aquiletour.core.pages.dashboard.teacher.views.TeacherDashboardView;
import ca.aquiletour.core.pages.dashboard.views.DashboardView;
import ca.ntro.core.system.trace.T;

public class DashboardControllerTeacher extends DashboardController {

	@Override
	protected Class<? extends DashboardView> viewClass() {
		T.call(this);

		return TeacherDashboardView.class;
	}

	@Override
	protected void installParentViewMessageHandler() {
		T.call(this);
		
		addParentViewMessageHandler(ShowTeacherDashboardMessage.class, new ShowTeacherDashboardHandler());
		addSubViewLoader(DashboardCourseViewTeacher.class, context().lang());
		
		addModelViewSubViewHandler(DashboardCourseViewTeacher.class, new DashboardViewModel());
	}

	@Override
	protected Class<? extends Dashboard> modelClass() {
		T.call(this);

		return DashboardTeacher.class;
	}
}
