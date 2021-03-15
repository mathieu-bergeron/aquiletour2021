package ca.aquiletour.core.pages.root;

import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.mvc.WindowViewHandler;
import ca.ntro.core.system.trace.T;

public class RootViewHandler extends WindowViewHandler<AiguilleurRootView> {
	
	
	@Override
	public void handle(NtroWindow window, AiguilleurRootView view) {
		T.call(this);
		
		window.installRootView(view);
	}
}
