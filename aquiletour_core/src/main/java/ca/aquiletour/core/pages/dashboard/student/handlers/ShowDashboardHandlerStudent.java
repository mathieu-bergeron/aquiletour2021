package ca.aquiletour.core.pages.dashboard.student.handlers;

import ca.aquiletour.core.pages.dashboard.student.messages.ShowDashboardMessageStudent;
import ca.aquiletour.core.pages.dashboard.student.views.DashboardViewStudent;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ParentViewMessageHandler;

public class ShowDashboardHandlerStudent extends ParentViewMessageHandler<RootView,
                                                                   DashboardViewStudent,
                                                                   ShowDashboardMessageStudent> {

	@Override
	protected void handle(RootView parentView, 
			              DashboardViewStudent currentView, 
			              ShowDashboardMessageStudent message) {
		
		parentView.showDashboard(currentView);
	}


}
