package ca.aquiletour.core.backend;

import ca.aquiletour.core.backend.handlers.AddAppointmentHandler;
import ca.aquiletour.core.backend.handlers.DeleteAppointmentHandler;
import ca.aquiletour.core.backend.handlers.MoveAppointmentHandler;
import ca.aquiletour.core.pages.queue.student.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.MoveAppointmentMessage;
import ca.ntro.core.mvc.BackendController;

public class QueueBackendController extends BackendController<RootBackendController>{

	//TODO MAP de STRING vers timer, un timer par queue
	@Override
	protected void onCreate() {
		
		addMessageHandler(AddAppointmentMessage.class, new AddAppointmentHandler());
		addMessageHandler(DeleteAppointmentMessage.class, new DeleteAppointmentHandler());
		addMessageHandler(MoveAppointmentMessage.class, new MoveAppointmentHandler());
		//message teacherusesQueue -> isQUEUEOPEN = true-> qq timer fini -> false -> visiter chque queuesummary pr update a false

	}

	@Override
	protected void onFailure(Exception e) {
		
	}

}
