package ca.aquiletour.core.pages.queue;

import ca.aquiletour.core.pages.queue.messages.AddAppointmentHandler;
import ca.aquiletour.core.pages.queue.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.messages.DeleteAppointmentHandler;
import ca.aquiletour.core.pages.queue.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.messages.ShowQueueHandler;
import ca.aquiletour.core.pages.queue.messages.ShowQueueMessage;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.services.stores.LocalStore;

public class QueueController extends NtroController<RootController> {

	@Override
	protected void initialize(NtroContext context) {

		setViewLoader(QueueView.class, context.getLang());
		
		setModelLoader(LocalStore.getLoader(QueueModel.class, context.getAuthToken(), context.getUserId()));
		
		addParentViewMessageHandler(ShowQueueMessage.class, new ShowQueueHandler());
		
		addModelMessageHandler(AddAppointmentMessage.class, new AddAppointmentHandler());

		addModelMessageHandler(DeleteAppointmentMessage.class, new DeleteAppointmentHandler());

		addSubViewLoader(AppointmentView.class, context.getLang());
		
		addModelViewSubViewHandler(AppointmentView.class, new QueueViewModel());
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
