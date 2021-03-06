package ca.aquiletour.core.pages.root.handlers;

import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.mvc.WindowViewHandler;
import ca.ntro.core.system.trace.T;

public class RootViewHandler extends WindowViewHandler<RootView> {
	
	
	@Override
	public void handle(NtroWindow window, RootView view) {
		T.call(this);
		
		window.installRootView(view);
	}
}
