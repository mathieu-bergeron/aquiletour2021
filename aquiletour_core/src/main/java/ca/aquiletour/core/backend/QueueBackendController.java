package ca.aquiletour.core.backend;

import ca.aquiletour.core.backend.handlers.AddAppointmentHandler;
import ca.aquiletour.core.pages.queue.messages.AddAppointmentMessage;
import ca.ntro.core.mvc.BackendController;

public class QueueBackendController extends BackendController<RootBackendController>{

	@Override
	protected void onCreate() {
		
		addMessageHandler(AddAppointmentMessage.class, new AddAppointmentHandler());

	}

	@Override
	protected void onFailure(Exception e) {
		
	}

}
