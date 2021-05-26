package ca.aquiletour.core.pages.queue.handlers;

import ca.aquiletour.core.pages.queue.QueueController;
import ca.aquiletour.core.pages.queue.messages.ShowQueueMessage;
import ca.aquiletour.core.pages.queue.models.QueueModel;
import ca.aquiletour.core.pages.queue.views.QueueView;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ControllerMessageHandler;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;

public class ShowQueueHandler<V extends QueueView, MSG extends ShowQueueMessage> extends ControllerMessageHandler<QueueController, V, MSG> {

	private String currentQueueId = null;

	@Override
	protected void handle(QueueController currentController, V currentView, MSG message) {
		T.call(this);
		
		String queueId = message.getQueueId();
		
		MustNot.beNull(queueId);

		if(!queueId.equals(currentQueueId)) {
			String authToken = currentController.context().user().getAuthToken();
			currentController.setModelLoader(QueueModel.class, authToken, queueId);
			currentQueueId = queueId;
		}
		
		RootView rootView = (RootView) currentController.getParentController().getView();
		rootView.showQueue(currentView);
	}
}
