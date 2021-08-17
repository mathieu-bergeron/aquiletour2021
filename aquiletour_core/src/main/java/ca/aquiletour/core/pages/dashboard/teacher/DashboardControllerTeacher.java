package ca.aquiletour.core.pages.dashboard.teacher;

import ca.aquiletour.core.pages.dashboard.DashboardController;
import ca.aquiletour.core.pages.dashboard.models.DashboardModel;
import ca.aquiletour.core.pages.dashboard.teacher.handlers.DashboardViewModelTeacher;
import ca.aquiletour.core.pages.dashboard.teacher.handlers.ShowDashboardHandlerTeacher;
import ca.aquiletour.core.pages.dashboard.teacher.messages.ShowTeacherDashboardMessage;
import ca.aquiletour.core.pages.dashboard.teacher.models.DashboardModelTeacher;
import ca.aquiletour.core.pages.dashboard.teacher.views.DashboardCourseViewTeacher;
import ca.aquiletour.core.pages.dashboard.teacher.views.DashboardViewTeacher;
import ca.aquiletour.core.pages.dashboard.views.DashboardView;
import ca.ntro.core.system.trace.T;

public class DashboardControllerTeacher extends DashboardController {

	@Override
	protected Class<? extends DashboardView> viewClass() {
		T.call(this);

		return DashboardViewTeacher.class;
	}

	@Override
	protected void installParentViewMessageHandler() {
		T.call(this);
		
		addParentViewMessageHandler(ShowTeacherDashboardMessage.class, new ShowDashboardHandlerTeacher());
		addSubViewLoader(DashboardCourseViewTeacher.class, context().lang());
		
		addModelViewSubViewHandler(DashboardCourseViewTeacher.class, new DashboardViewModelTeacher());
	}

	@Override
	protected Class<? extends DashboardModel<?>> modelClass() {
		T.call(this);

		return DashboardModelTeacher.class;
	}
}
