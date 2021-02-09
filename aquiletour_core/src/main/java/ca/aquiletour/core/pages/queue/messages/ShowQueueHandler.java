package ca.aquiletour.core.pages.queue.messages;

import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ParentViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class ShowQueueHandler extends ParentViewMessageHandler<RootView, QueueView, ShowQueueMessage> {
	
	@Override
	protected void handle(RootView parentView, QueueView currentView, ShowQueueMessage message) {
		T.call(this);
		
		String courseId = message.getCourseId();
		String groupId = message.getGroupId();
		
		parentView.showQueue(currentView);
		
	}

}
