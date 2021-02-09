package ca.aquiletour.core.pages.queue.messages;

import ca.aquiletour.core.pages.queue.QueueController;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ControllerParentViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class ShowQueueHandler extends ControllerParentViewMessageHandler<QueueController, RootView, QueueView, ShowQueueMessage> {
	
	@Override
	protected void handle(QueueController controller, RootView parentView, QueueView currentView, ShowQueueMessage message) {
		T.call(this);
		
		String courseId = message.getCourseId();
		String groupId = message.getGroupId();

		controller.loadModel(courseId, groupId);
		
		parentView.showQueue(currentView);
		
	}

}
