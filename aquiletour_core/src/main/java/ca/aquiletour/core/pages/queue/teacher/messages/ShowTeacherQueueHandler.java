package ca.aquiletour.core.pages.queue.teacher.messages;

import ca.aquiletour.core.pages.queue.QueueController;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ControllerMessageHandler;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;

public class ShowTeacherQueueHandler extends ControllerMessageHandler<QueueController, QueueView, ShowTeacherQueueMessage> {
	
	private String currentCourseId = null;
	
	@Override
	protected void handle(QueueController currentController, QueueView currentView, ShowTeacherQueueMessage message) {
		T.call(this);
		
		String courseId = message.getCourseId();
		
		MustNot.beNull(courseId);
		
		if(!courseId.equals(currentCourseId)) {
			// XXX: change queue model when needed
			String authToken = currentController.currentContext().user().getAuthToken();
			currentController.setModelLoader(QueueModel.class, authToken, courseId);
			currentCourseId = courseId;
		}

		RootView rootView = (RootView) currentController.getParentController().getView();
		rootView.showQueue(currentView);
	}

}
