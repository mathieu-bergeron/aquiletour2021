package ca.aquiletour.core.pages.root;

import ca.ntro.core.mvc.ViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class DisplayErrorHandler extends ViewMessageHandler<RootView, DisplayErrorMessage> {

	@Override
	protected void handle(RootView view, DisplayErrorMessage message) {
		T.call(this);
		
		T.here();
		
		view.displayErrorMessage(message.getMessage());
	}

}
