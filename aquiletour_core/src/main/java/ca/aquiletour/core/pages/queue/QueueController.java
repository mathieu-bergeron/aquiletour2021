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
import ca.ntro.core.system.trace.T;

public class QueueController extends NtroController<RootController> {

	@Override
	protected void onCreate(NtroContext currentContext) {
		T.call(this);

		setViewLoader(QueueView.class, currentContext.getLang());

		addSubViewLoader(AppointmentView.class, currentContext.getLang());
		

		// TODO: add a ModelToBeDetermined loader. A place holder to be unlocked 
		//       when the real model is loaded
		//setModelLoader(LocalStore.getLoader(QueueModel.class, currentContext.getAuthToken(), courseId, groupId));

		addModelMessageHandler(AddAppointmentMessage.class, new AddAppointmentHandler());

		addModelMessageHandler(DeleteAppointmentMessage.class, new DeleteAppointmentHandler());

		addModelViewSubViewHandler(AppointmentView.class, new QueueViewModel());

		//addControllerParentViewMessageHandler(ShowQueueMessage.class, new ShowQueueHandler());
		
	}

	@Override
	protected void onChangeContext(NtroContext previousContext, NtroContext currentContext) {
		T.call(this);

	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
