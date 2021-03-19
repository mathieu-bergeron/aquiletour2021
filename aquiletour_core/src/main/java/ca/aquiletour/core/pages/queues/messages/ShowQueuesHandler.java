package ca.aquiletour.core.pages.queues.messages;

import ca.aquiletour.core.pages.queues.QueuesView;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ParentViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class ShowQueuesHandler extends ParentViewMessageHandler<RootView, QueuesView, ShowQueuesMessage> {

	@Override
	protected void handle(RootView parentView, QueuesView currentView, ShowQueuesMessage message) {
		
		parentView.showQueues(currentView);
	}
	

}
