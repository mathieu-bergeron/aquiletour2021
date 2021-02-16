package ca.aquiletour.core.backend;

import ca.aquiletour.core.backend.handlers.AddCourseHandler;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.ntro.core.mvc.BackendController;

public class DashboardBackendController extends BackendController<RootBackendController>{

	@Override
	protected void onCreate() {
		
		addMessageHandler(AddCourseMessage.class, new AddCourseHandler());

	}

	@Override
	protected void onFailure(Exception e) {
		
	}

}
