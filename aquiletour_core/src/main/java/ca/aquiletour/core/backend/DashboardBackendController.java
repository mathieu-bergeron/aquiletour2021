package ca.aquiletour.core.backend;

import ca.aquiletour.core.backend.handlers.AddCourseHandler;
import ca.aquiletour.core.backend.handlers.DeleteCourseHandler;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboards.teacher.messages.DeleteCourseMessage;
import ca.ntro.core.mvc.BackendController;

public class DashboardBackendController extends BackendController<RootBackendController>{

	@Override
	protected void onCreate() {
		
		addMessageHandler(AddCourseMessage.class, new AddCourseHandler());
		addMessageHandler(DeleteCourseMessage.class, new DeleteCourseHandler());
		
		// TODO: other backend messages, e.g.
		//       DeleteCourseMessage
		//       AddUserMessage

	}

	@Override
	protected void onFailure(Exception e) {
		
	}

}
