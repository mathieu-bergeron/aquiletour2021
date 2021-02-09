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
	protected void onCreate(NtroContext context) {
		T.call(this);

		setViewLoader(QueueView.class, context.getLang());

		addParentViewMessageHandler(ShowQueueMessage.class, new ShowQueueHandler());

		addSubViewLoader(AppointmentView.class, context.getLang());
		
		setModelHandlers(context);
		
	}

	private void setModelHandlers(NtroContext context) {
		T.call(this);

		if(context.getPath().size() >= 2) {
			String courseId = context.getPath().getName(0);
			String groupId = context.getPath().getName(1);
			setModelLoader(LocalStore.getLoader(QueueModel.class, context.getAuthToken(), courseId, groupId));

			addModelMessageHandler(AddAppointmentMessage.class, new AddAppointmentHandler());

			addModelMessageHandler(DeleteAppointmentMessage.class, new DeleteAppointmentHandler());

			addModelViewSubViewHandler(AppointmentView.class, new QueueViewModel());
		}
	}

	@Override
	protected void onChangeContext(NtroContext previousContext, NtroContext context) {
		T.call(this);
		
		if(!context.hasSamePath(previousContext)) {
			setModelHandlers(context);
		}

	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
