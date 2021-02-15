package ca.aquiletour.core.backend;

import ca.aquiletour.core.backend.handlers.AddCourseHandler;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.ntro.core.mvc.BackendController;

public class DashboardBackendController extends BackendController<RootBackendController>{

	@Override
	protected void onCreate() {
		
		addMessageHandler(AddCourseMessage.class, new AddCourseHandler());

		/*
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
		*/

	}

	@Override
	protected void onFailure(Exception e) {
		
	}

}
