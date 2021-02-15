package ca.aquiletour.core.pages.queues;

import ca.aquiletour.core.pages.queue.AppointmentView;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.queue.handlers.AddAppointmentHandler;
import ca.aquiletour.core.pages.queue.handlers.DeleteAppointmentHandler;
import ca.aquiletour.core.pages.queue.handlers.QueueViewModel;
import ca.aquiletour.core.pages.queue.handlers.ShowQueueHandler;
import ca.aquiletour.core.pages.queue.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.messages.ShowQueueMessage;
import ca.aquiletour.core.pages.queues.messages.ShowQueuesHandler;
import ca.aquiletour.core.pages.queues.messages.ShowQueuesMessage;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.models.EmptyModelLoader;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.services.stores.LocalStore;
import ca.ntro.core.services.stores.NetworkStore;
import ca.ntro.core.system.trace.T;

public class QueuesController extends NtroController<RootController> {

	@Override
	protected void onCreate() {
		T.call(this);

		setViewLoader(QueuesView.class, "fr");
		
		setModelLoader(NetworkStore.getLoader(QueuesModel.class, "TODO", "allQueues"));
		
		addParentViewMessageHandler(ShowQueuesMessage.class, new ShowQueuesHandler());
		
		// TODO
	}

	@Override
	protected void onChangeContext(NtroContext previousContext) {
		T.call(this);
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
