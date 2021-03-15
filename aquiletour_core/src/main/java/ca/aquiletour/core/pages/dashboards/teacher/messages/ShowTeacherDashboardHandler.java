package ca.aquiletour.core.pages.dashboards.teacher.messages;

import ca.aquiletour.core.pages.dashboards.teacher.TeacherDashboardView;
import ca.aquiletour.core.pages.root.AiguilleurRootView;
import ca.ntro.core.mvc.ParentViewMessageHandler;

public class ShowTeacherDashboardHandler extends ParentViewMessageHandler<AiguilleurRootView,
                                                                   TeacherDashboardView,
                                                                   ShowTeacherDashboardMessage> {

	@Override
	protected void handle(AiguilleurRootView parentView, 
			              TeacherDashboardView currentView, 
			              ShowTeacherDashboardMessage message) {

		parentView.showDashboard(currentView);
	}


}
