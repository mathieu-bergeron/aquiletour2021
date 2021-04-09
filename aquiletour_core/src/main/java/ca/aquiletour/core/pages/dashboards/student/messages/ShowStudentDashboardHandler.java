package ca.aquiletour.core.pages.dashboards.student.messages;

import ca.aquiletour.core.pages.dashboards.student.DashboardViewStudent;
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
