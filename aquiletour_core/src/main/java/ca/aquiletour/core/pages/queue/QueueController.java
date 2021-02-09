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
	protected void initialize(NtroContext context) {
		T.call(this);

		setViewLoader(QueueView.class, context.getLang());
		
		String courseId = context.getPath().getName(0);
		String groupId = context.getPath().getName(1);
		setModelLoader(LocalStore.getLoader(QueueModel.class, context.getAuthToken(), courseId, groupId));
		
		addParentViewMessageHandler(ShowQueueMessage.class, new ShowQueueHandler());
		
		addModelMessageHandler(AddAppointmentMessage.class, new AddAppointmentHandler());

		addModelMessageHandler(DeleteAppointmentMessage.class, new DeleteAppointmentHandler());

		addSubViewLoader(AppointmentView.class, context.getLang());
		
		addModelViewSubViewHandler(AppointmentView.class, new QueueViewModel());
		
	@Override
	protected void changeContext(NtroContext oldContext, NtroContext newContext) {
		T.call(this);
		
		if(!oldContext.getPath().equals(newContext.getPath())) {
			String courseId = newContext.getPath().getName(0);
			String groupId = newContext.getPath().getName(1);
			resetModelLoader(LocalStore.getLoader(QueueModel.class, newContext.getAuthToken(), courseId, groupId));
		}
		

	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
