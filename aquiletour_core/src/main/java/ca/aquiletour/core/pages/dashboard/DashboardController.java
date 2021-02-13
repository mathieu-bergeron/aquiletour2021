package ca.aquiletour.core.pages.dashboard;

import ca.aquiletour.core.pages.dashboard.messages.AddCourseHandler;
import ca.aquiletour.core.pages.dashboard.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboard.messages.ShowDashboardHandler;
import ca.aquiletour.core.pages.dashboard.messages.ShowDashboardMessage;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.services.stores.NetworkStore;
import ca.ntro.core.system.trace.T;

public class DashboardController extends NtroController<RootController> {

	@Override
	protected void onCreate() {
		T.call(this);

		setViewLoader(DashboardView.class, currentContext().getLang());
		
		setModelLoader(NetworkStore.getLoader(DashboardModel.class, currentContext().getAuthToken(), currentContext().getUserId()));
		
		addParentViewMessageHandler(ShowDashboardMessage.class, new ShowDashboardHandler());
		

		addSubViewLoader(CourseSummaryView.class, currentContext().getLang());
		
		addModelViewSubViewHandler(CourseSummaryView.class, new DashboardViewModel());
		
		// TODO: add model handler to pre-load models of each courses
		//       on the server, model pre-loading does nothing (or is restricted by path)
	}

	@Override
	protected void onChangeContext(NtroContext previousContext) {
		T.call(this);
		
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);

	}


}
