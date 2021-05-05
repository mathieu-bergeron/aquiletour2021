package ca.aquiletour.core.pages.root.handlers;

import ca.aquiletour.core.pages.root.RootView;
import ca.aquiletour.core.pages.root.messages.ShowLoginMenuMessage;
import ca.ntro.core.mvc.ViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class ShowLoginMenuHandler extends ViewMessageHandler<RootView, ShowLoginMenuMessage> {

	@Override
	protected void handle(RootView view, ShowLoginMenuMessage message) {
		T.call(this);
		
		view.showLoginMenu(message.getMessageToUser(), message.getDelayedMessages());
	}

}
