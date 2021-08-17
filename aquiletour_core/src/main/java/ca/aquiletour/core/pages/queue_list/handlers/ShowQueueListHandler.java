package ca.aquiletour.core.pages.queue_list.handlers;

import ca.aquiletour.core.pages.queue_list.messages.ShowQueueListMessage;
import ca.aquiletour.core.pages.queue_list.views.QueueListView;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ParentViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class ShowQueueListHandler extends ParentViewMessageHandler<RootView, QueueListView, ShowQueueListMessage> {

	@Override
	protected void handle(RootView parentView, QueueListView currentView, ShowQueueListMessage message) {
		T.call(this);
		
		parentView.showQueues(QueueListView.class, currentView);
	}
	

}
