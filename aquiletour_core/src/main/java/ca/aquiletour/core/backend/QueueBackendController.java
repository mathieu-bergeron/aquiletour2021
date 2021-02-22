package ca.aquiletour.core.backend;

import ca.aquiletour.core.backend.handlers.AddAppointmentHandler;
import ca.aquiletour.core.backend.handlers.DeleteAppointmentHandler;
import ca.aquiletour.core.pages.queue.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.messages.DeleteAppointmentMessage;
import ca.ntro.core.mvc.BackendController;

public class QueueBackendController extends BackendController<RootBackendController>{

	@Override
	protected void onCreate() {
		
		addMessageHandler(AddAppointmentMessage.class, new AddAppointmentHandler());
		addMessageHandler(DeleteAppointmentMessage.class, new DeleteAppointmentHandler());

	}

	@Override
	protected void onFailure(Exception e) {
		
	}

}
