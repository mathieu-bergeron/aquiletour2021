package ca.aquiletour.core.pages.dashboard.messages;

import ca.aquiletour.core.pages.dashboard.DashboardView;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ParentViewMessageHandler;

public class ShowDashboardHandler extends ParentViewMessageHandler<RootView,
                                                                   DashboardView,
                                                                   ShowDashboardMessage> {

	@Override
	protected void handle(RootView parentView, 
			              DashboardView currentView, 
			              ShowDashboardMessage message) {
		
		parentView.showDashboard(currentView);
	}


}
