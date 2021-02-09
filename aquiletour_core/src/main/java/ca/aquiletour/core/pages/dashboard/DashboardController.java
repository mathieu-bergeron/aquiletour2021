package ca.aquiletour.core.pages.dashboard;

import ca.aquiletour.core.pages.dashboard.messages.AddCourseHandler;
import ca.aquiletour.core.pages.dashboard.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboard.messages.ShowDashboardHandler;
import ca.aquiletour.core.pages.dashboard.messages.ShowDashboardMessage;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.services.stores.LocalStore;
import ca.ntro.core.system.trace.T;

public class DashboardController extends NtroController<RootController> {

	@Override
	protected void onCreate(NtroContext currentContext) {
		T.call(this);

		setViewLoader(DashboardView.class, currentContext.getLang());
		
		setModelLoader(LocalStore.getLoader(DashboardModel.class, currentContext.getAuthToken(), currentContext.getUserId()));
		
		addParentViewMessageHandler(ShowDashboardMessage.class, new ShowDashboardHandler());
		
		addModelMessageHandler(AddCourseMessage.class, new AddCourseHandler());

		addSubViewLoader(CourseSummaryView.class, currentContext.getLang());
		
		addModelViewSubViewHandler(CourseSummaryView.class, new DashboardViewModel());
		
		// TODO: add model handler to pre-load models of each courses

	}

	@Override
	protected void onChangeContext(NtroContext previousContext, NtroContext currentContext) {
		T.call(this);
		
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);

	}


}
