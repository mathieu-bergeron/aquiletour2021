package ca.aquiletour.core.pages.root.handlers;

import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ViewMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.ntro_messages.NtroErrorMessage;

public class NtroErrorHandler extends ViewMessageHandler<RootView, NtroErrorMessage> {

	@Override
	protected void handle(RootView view, NtroErrorMessage message) {
		T.call(this);

		view.displayErrorMessage(message.getMessage());
	}

}
