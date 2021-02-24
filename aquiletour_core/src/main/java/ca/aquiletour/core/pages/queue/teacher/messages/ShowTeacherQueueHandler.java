package ca.aquiletour.core.pages.queue.teacher.messages;

import ca.aquiletour.core.pages.queue.QueueController;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ControllerMessageHandler;
import ca.ntro.core.services.stores.NetworkStore;
import ca.ntro.core.system.trace.T;

public class ShowTeacherQueueHandler extends ControllerMessageHandler<QueueController, QueueView, ShowTeacherQueueMessage> {
	
	@Override
	protected void handle(QueueController currentController, QueueView currentView, ShowTeacherQueueMessage message) {
		T.call(this);
		
		System.out.println("SHOW QUEUE: " + currentController);
		
		String courseId = message.getCourseId();

		String authToken = currentController.currentContext().getUser().getAuthToken();
		
		currentController.setModelLoader(NetworkStore.getLoader(QueueModel.class, authToken, courseId));
		
		RootView rootView = (RootView) currentController.getParentController().getView();
		
		rootView.showQueue(currentView);
	}

}
