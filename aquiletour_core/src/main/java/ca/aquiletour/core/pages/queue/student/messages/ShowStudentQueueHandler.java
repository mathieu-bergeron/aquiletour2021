package ca.aquiletour.core.pages.queue.student.messages;

import ca.aquiletour.core.pages.queue.QueueController;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.root.AiguilleurRootView;
import ca.ntro.core.mvc.ControllerMessageHandler;
import ca.ntro.core.system.trace.T;

public class ShowStudentQueueHandler extends ControllerMessageHandler<QueueController, QueueView, ShowStudentQueueMessage> {
	
	@Override
	protected void handle(QueueController currentController, QueueView currentView, ShowStudentQueueMessage message) {
		T.call(this);

		String courseId = message.getCourseId();

		String authToken = currentController.currentContext().user().getAuthToken();
		
		currentController.setModelLoader(QueueModel.class, authToken, courseId);
		
		AiguilleurRootView rootView = (AiguilleurRootView) currentController.getParentController().getView();
		
		rootView.showQueue(currentView);
	}

}
