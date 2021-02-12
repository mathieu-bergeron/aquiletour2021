package ca.aquiletour.core.pages.queue;

import ca.aquiletour.core.pages.queue.handlers.AddAppointmentHandler;
import ca.aquiletour.core.pages.queue.handlers.DeleteAppointmentHandler;
import ca.aquiletour.core.pages.queue.handlers.QueueViewModel;
import ca.aquiletour.core.pages.queue.handlers.ShowQueueHandler;
import ca.aquiletour.core.pages.queue.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.messages.ShowQueueMessage;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class QueueController extends NtroController<RootController> {

	@Override
	protected void onCreate() {
		T.call(this);

		setViewLoader(QueueView.class, currentContext().getLang());

		addSubViewLoader(AppointmentView.class, currentContext().getLang());

		// (1) installing a ModelMessageHandler even if there is no modelLoader
		//      this means it will block until the model is loaded
		addModelMessageHandler(AddAppointmentMessage.class, new AddAppointmentHandler());

		addModelMessageHandler(DeleteAppointmentMessage.class, new DeleteAppointmentHandler());

		addModelViewSubViewHandler(AppointmentView.class, new QueueViewModel());

		// (2) the modelLoader is installed after a ShowQueueMessage!
		addControllerMessageHandler(ShowQueueMessage.class, new ShowQueueHandler());

	}

	@Override
	protected void onChangeContext(NtroContext previousContext) {
		T.call(this);
		
		// TODO: we can automatize this!
		//       «simply» reset the tasks with the new lang
		if(previousContext.hasDifferentLang(currentContext())) {
			setViewLoader(QueueView.class, currentContext().getLang());
			addSubViewLoader(AppointmentView.class, currentContext().getLang());
		}
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
