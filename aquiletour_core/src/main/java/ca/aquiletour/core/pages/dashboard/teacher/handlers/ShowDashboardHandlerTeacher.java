package ca.aquiletour.core.pages.dashboard.teacher.handlers;

import ca.aquiletour.core.pages.dashboard.teacher.messages.ShowTeacherDashboardMessage;
import ca.aquiletour.core.pages.dashboard.teacher.views.DashboardViewTeacher;
import ca.aquiletour.core.pages.dashboard.views.DashboardView;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ParentViewMessageHandler;

public class ShowDashboardHandlerTeacher extends ParentViewMessageHandler<RootView,
                                                                   DashboardViewTeacher,
                                                                   ShowTeacherDashboardMessage> {

	@Override
	protected void handle(RootView parentView, 
			              DashboardViewTeacher currentView, 
			              ShowTeacherDashboardMessage message) {
		

		parentView.showDashboard(DashboardViewTeacher.class, currentView);
	}


}
