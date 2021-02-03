package ca.aquiletour.core.pages.root;

import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.mvc.WindowViewHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;

public class RootViewHandler extends WindowViewHandler<RootView> {
	
	
	@Override
	public void handle(NtroWindow window, RootView view) {
		T.call(this);
		
		window.installRootView(view);
	}
}
