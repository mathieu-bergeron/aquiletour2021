package ca.aquiletour.core.pages.queue.teacher.messages;

import ca.aquiletour.core.pages.queue.QueueController;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.root.AiguilleurRootView;
import ca.ntro.core.mvc.ControllerMessageHandler;
import ca.ntro.core.system.trace.T;

public class ShowTeacherQueueHandler extends ControllerMessageHandler<QueueController, QueueView, ShowTeacherQueueMessage> {
	
	@Override
	protected void handle(QueueController currentController, QueueView currentView, ShowTeacherQueueMessage message) {
		T.call(this);
		
		String courseId = message.getCourseId();

		String authToken = currentController.currentContext().user().getAuthToken();
		
		currentController.setModelLoader(QueueModel.class, authToken, courseId);
		
		AiguilleurRootView rootView = (AiguilleurRootView) currentController.getParentController().getView();
		
		rootView.showQueue(currentView);
	}

}
