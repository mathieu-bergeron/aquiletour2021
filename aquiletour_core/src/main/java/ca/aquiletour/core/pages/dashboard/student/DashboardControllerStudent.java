package ca.aquiletour.core.pages.dashboard.student;

import ca.aquiletour.core.pages.dashboard.DashboardController;
import ca.aquiletour.core.pages.dashboard.models.DashboardModel;
import ca.aquiletour.core.pages.dashboard.student.handlers.DashboardViewModelStudent;
import ca.aquiletour.core.pages.dashboard.student.handlers.ShowDashboardHandlerStudent;
import ca.aquiletour.core.pages.dashboard.student.messages.ShowDashboardMessageStudent;
import ca.aquiletour.core.pages.dashboard.student.models.DashboardModelStudent;
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
		
		addParentViewMessageHandler(ShowDashboardMessageStudent.class, new ShowDashboardHandlerStudent());
		
		addSubViewLoader(DashboardCourseViewStudent.class, context().lang());
		
		addModelViewSubViewHandler(DashboardCourseViewStudent.class, new DashboardViewModelStudent());
	}

	@Override
	protected Class<? extends DashboardModel<?>> modelClass() {
		T.call(this);
		
		return DashboardModelStudent.class;
	}
}
