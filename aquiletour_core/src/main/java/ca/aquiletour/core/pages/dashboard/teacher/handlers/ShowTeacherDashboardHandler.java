package ca.aquiletour.core.pages.dashboard.teacher.handlers;

import ca.aquiletour.core.pages.dashboard.teacher.messages.ShowTeacherDashboardMessage;
import ca.aquiletour.core.pages.dashboard.teacher.views.TeacherDashboardView;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ParentViewMessageHandler;

public class ShowTeacherDashboardHandler extends ParentViewMessageHandler<RootView,
                                                                   TeacherDashboardView,
                                                                   ShowTeacherDashboardMessage> {

	@Override
	protected void handle(RootView parentView, 
			              TeacherDashboardView currentView, 
			              ShowTeacherDashboardMessage message) {
		
		System.out.println("ShowTeacherDashboard");

		parentView.showDashboard(currentView);
	}


}
