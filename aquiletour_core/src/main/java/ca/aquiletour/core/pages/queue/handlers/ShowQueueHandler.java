package ca.aquiletour.core.pages.queue.handlers;

import ca.aquiletour.core.pages.queue.QueueController;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.queue.messages.ShowQueueMessage;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ControllerMessageHandler;
import ca.ntro.core.services.stores.NetworkStore;
import ca.ntro.core.system.trace.T;

public class ShowQueueHandler extends ControllerMessageHandler<QueueController, QueueView, ShowQueueMessage> {
	
	@Override
	protected void handle(QueueController currentController, QueueView currentView, ShowQueueMessage message) {
		T.call(this);
		
		System.out.println("SHOW QUEUE: " + currentController);
		
		String courseId = message.getCourseId();

		String authToken = currentController.currentContext().getUser().getAuthToken();
		
		currentController.setModelLoader(NetworkStore.getLoader(QueueModel.class, authToken, courseId));
		
		RootView rootView = (RootView) currentController.getParentController().getView();
		
		rootView.showQueue(currentView);
	}

}
