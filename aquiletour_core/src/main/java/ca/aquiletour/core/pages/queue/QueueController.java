package ca.aquiletour.core.pages.queue;

import ca.aquiletour.core.pages.queue.messages.AddAppointmentHandler;
import ca.aquiletour.core.pages.queue.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.messages.DeleteAppointmentHandler;
import ca.aquiletour.core.pages.queue.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.messages.ShowQueueHandler;
import ca.aquiletour.core.pages.queue.messages.ShowQueueMessage;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.models.LoadModelLater;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.services.stores.LocalStore;
import ca.ntro.core.system.trace.T;

public class QueueController extends NtroController<RootController> {

	@Override
	protected void onCreate() {
		T.call(this);

		setViewLoader(QueueView.class, getContext().getLang());

		addSubViewLoader(AppointmentView.class, getContext().getLang());
		
		setModelLoader(new LoadModelLater()); // TODO: should be added by default

		addModelMessageHandler(AddAppointmentMessage.class, new AddAppointmentHandler());

		addModelMessageHandler(DeleteAppointmentMessage.class, new DeleteAppointmentHandler());

		addModelViewSubViewHandler(AppointmentView.class, new QueueViewModel());

		addControllerParentViewMessageHandler(ShowQueueMessage.class, new ShowQueueHandler());

	}

	public void loadModel(String courseId, String groupId) {
		T.call(this);
		
		String authToken = getContext().getAuthToken();

		setModelLoader(LocalStore.getLoader(QueueModel.class, authToken, courseId, groupId));
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
