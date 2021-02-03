package ca.aquiletour.core.pages.dashboard;

import ca.aquiletour.core.pages.dashboard.messages.AddCourseHandler;
import ca.aquiletour.core.pages.dashboard.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboard.messages.ShowDashboardHandler;
import ca.aquiletour.core.pages.dashboard.messages.ShowDashboardMessage;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.mvc.ViewLoaders;
import ca.ntro.core.services.stores.LocalStore;
import ca.ntro.core.system.trace.T;

public class DashboardController extends NtroController<RootController> {

	@Override
	protected void initialize() {
		T.call(this);

		setViewLoader(ViewLoaders.getViewLoader(DashboardView.class, "fr"));
		
		setModelLoader(LocalStore.getLoader(DashboardModel.class, "TODO"));
		
		setViewModelHandler(new DashboardViewModel());

		addParentViewMessageHandler(ShowDashboardMessage.class, new ShowDashboardHandler());
		
		addModelMessageHandler(AddCourseMessage.class, new AddCourseHandler());
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);
		
	}

}
