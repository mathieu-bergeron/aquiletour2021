package ca.aquiletour.core.backend;

import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseHandler;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.ntro.core.mvc.EmptyViewLoader;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.services.stores.NetworkStore;

public class DashboardBackendController extends NtroController<DashboardBackendController>{

	@Override
	protected void onCreate() {

		// FIXME: should not be needed, we should have a BackendController
		setViewLoader(new EmptyViewLoader());
		
		// FIXME: there should be a single backend controller
		//        hence it should not get 
		//        but rather get context info on a per message basis
		setModelLoader(NetworkStore.getLoader(DashboardModel.class, 
				                              currentContext().getUser().getAuthToken(), 
				                              currentContext().getUser().getId()));

		// FIXME: in the backend, should not be a ModelMessageHandler
		//        only a MessageHandler and context info in the message
		addModelMessageHandler(AddCourseMessage.class, new AddCourseHandler());

	}

	@Override
	protected void onChangeContext(NtroContext previousContext) {
		
	}

	@Override
	protected void onFailure(Exception e) {
		
	}

}
