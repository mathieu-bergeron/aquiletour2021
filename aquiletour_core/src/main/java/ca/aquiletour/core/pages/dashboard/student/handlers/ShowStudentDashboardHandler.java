package ca.aquiletour.core.pages.dashboard.student.handlers;

import ca.aquiletour.core.pages.dashboard.student.messages.ShowStudentDashboardMessage;
import ca.aquiletour.core.pages.dashboard.student.views.DashboardViewStudent;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ParentViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class ShowStudentDashboardHandler extends ParentViewMessageHandler<RootView,
                                                                   DashboardViewStudent,
                                                                   ShowStudentDashboardMessage> {

	@Override
	protected void handle(RootView parentView, 
			              DashboardViewStudent currentView, 
			              ShowStudentDashboardMessage message) {
		
		parentView.showDashboard(currentView);
	}


}
