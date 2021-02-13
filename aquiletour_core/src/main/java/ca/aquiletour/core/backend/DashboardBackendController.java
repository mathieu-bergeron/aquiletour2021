package ca.aquiletour.core.backend;

import ca.aquiletour.core.pages.dashboard.DashboardModel;
import ca.aquiletour.core.pages.dashboard.messages.AddCourseHandler;
import ca.aquiletour.core.pages.dashboard.messages.AddCourseMessage;
import ca.ntro.core.mvc.EmptyViewLoader;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.services.stores.NetworkStore;

public class DashboardBackendController extends NtroController<DashboardBackendController>{

	@Override
	protected void onCreate() {

		// FIXME: should not be needed, we should have a BackendController
		setViewLoader(new EmptyViewLoader());
		
		setModelLoader(NetworkStore.getLoader(DashboardModel.class, currentContext().getAuthToken(), currentContext().getUserId()));

		addModelMessageHandler(AddCourseMessage.class, new AddCourseHandler());
		
	}

	@Override
	protected void onChangeContext(NtroContext previousContext) {
		
	}

	@Override
	protected void onFailure(Exception e) {
		
	}

}
