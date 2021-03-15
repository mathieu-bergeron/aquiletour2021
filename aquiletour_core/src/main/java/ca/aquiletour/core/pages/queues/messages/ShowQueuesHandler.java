package ca.aquiletour.core.pages.queues.messages;

import ca.aquiletour.core.pages.queues.QueuesView;
import ca.aquiletour.core.pages.root.AiguilleurRootView;
import ca.aquiletour.core.pages.users.UsersView;
import ca.aquiletour.core.pages.users.messages.ShowUsersMessage;
import ca.ntro.core.mvc.ParentViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class ShowQueuesHandler extends ParentViewMessageHandler<AiguilleurRootView, QueuesView, ShowQueuesMessage> {

	@Override
	protected void handle(AiguilleurRootView parentView, QueuesView currentView, ShowQueuesMessage message) {
		
		parentView.showQueues(currentView);
	}
	

}
