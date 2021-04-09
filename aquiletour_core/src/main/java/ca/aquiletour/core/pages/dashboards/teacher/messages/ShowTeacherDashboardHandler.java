package ca.aquiletour.core.pages.dashboards.teacher.messages;

import ca.aquiletour.core.pages.dashboards.teacher.TeacherDashboardView;
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
