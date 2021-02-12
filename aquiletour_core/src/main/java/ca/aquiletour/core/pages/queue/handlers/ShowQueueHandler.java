package ca.aquiletour.core.pages.queue.handlers;

import ca.aquiletour.core.pages.queue.QueueController;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.queue.messages.ShowQueueMessage;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ControllerMessageHandler;
import ca.ntro.core.services.stores.LocalStore;
import ca.ntro.core.system.trace.T;

public class ShowQueueHandler extends ControllerMessageHandler<QueueController, QueueView, ShowQueueMessage> {
	
	@Override
	protected void handle(QueueController currentController, QueueView currentView, ShowQueueMessage message) {
		T.call(this);
		
		String courseId = message.getCourseId();
		String groupId = message.getGroupId();

		String authToken = currentController.currentContext().getAuthToken();
		
		// TODO: load model according to the message
		//currentController.setModelLoader(LocalStore.getLoader(QueueModel.class, authToken, courseId, groupId));
		
		RootView rootView = (RootView) currentController.getParentController().getView();
		
		rootView.showQueue(currentView);
	}

}
