package ca.aquiletour.core.pages.root.handlers;

import ca.aquiletour.core.messages.user.ShowPasswordMenu;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class ShowPasswordMenuHandler extends ViewMessageHandler<RootView, ShowPasswordMenu> {

	@Override
	protected void handle(RootView view, ShowPasswordMenu message) {
		T.call(this);

		view.showPasswordMenu();
	}
}
