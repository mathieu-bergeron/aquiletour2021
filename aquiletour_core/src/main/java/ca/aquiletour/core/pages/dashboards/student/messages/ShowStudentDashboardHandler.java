package ca.aquiletour.core.pages.dashboards.student.messages;

import ca.aquiletour.core.pages.dashboards.student.StudentDashboardView;
import ca.aquiletour.core.pages.root.AiguilleurRootView;
import ca.ntro.core.mvc.ParentViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class ShowStudentDashboardHandler extends ParentViewMessageHandler<AiguilleurRootView,
                                                                   StudentDashboardView,
                                                                   ShowStudentDashboardMessage> {

	@Override
	protected void handle(AiguilleurRootView parentView, 
			              StudentDashboardView currentView, 
			              ShowStudentDashboardMessage message) {
		
		parentView.showDashboard(currentView);
	}


}
