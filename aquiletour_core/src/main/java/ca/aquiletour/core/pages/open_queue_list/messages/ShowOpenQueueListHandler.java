package ca.aquiletour.core.pages.open_queue_list.messages;

import ca.aquiletour.core.pages.open_queue_list.OpenQueueListView;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ParentViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class ShowOpenQueueListHandler extends ParentViewMessageHandler<RootView, OpenQueueListView, ShowOpenQueueListMessage> {

	@Override
	protected void handle(RootView parentView, OpenQueueListView currentView, ShowOpenQueueListMessage message) {
		T.call(this);
		
		parentView.showQueues(currentView);
	}
	

}
